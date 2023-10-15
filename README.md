# Despliegue de la aplicación

A continuación se hará una explicación de qué es necesario para desplegar la
aplicación y se acompañará con un ejemplo de despliegue en una máquina con
Linux/Ubuntu.

La aplicación se compone de tres partes: BBDD, backend y frontend. Primero
configuraremos cada una y luego las uniremos para que funcionen en conjunto.

## Configuración de los elementos de la aplicación

### BBDD

La aplicación utiliza MariaDB como motor de BBDD.

Para este ejemplo de despliegue instalaremos y configuraremos la BBDD en el
mismo servidor que hosteará el backend.

Primero instalaremos MariaDB:

```shell
sudo apt update
sudo apt install mariadb-server
```

Luego iniciaremos su servicio para poder conectarnos a ella:

```shell
sudo systemctl start mariadb
```

Es conveniente configurar el servicio de MariaDB para que se inicie junto al
boot del sistema:

```shell
sudo systemctl enable mariadb
```

La aplicación utilizará una base de datos con nombre `pandalearn`; Para
poblar la base de datos con las tablas y datos necesarios se utilizará el
archivo `database.sql` que se encuentra en el root del proyecto.

> ### **_Atención!_**
>
> El archivo `database.sql` borrará todo el contenido de la BBDD `pandalearn`
> y la dejará con sus datos por defecto. No se debe aplicar a una BBDD de
> producción.

Para aplicar el archivo debe accederse a MariaDB con el siguiente commando
_(Al pulsar enter se debe introducir la contraseña que se configuró para
root al momento de instalar MariaDB; al escribir la terminal capturará los
caracteres de forma silenciosa, sin dar feedback de usuario, pero la
contraseña estará siendo introducida)_:

```shell
sudo mysql -u root -p
```

En el prompt de MariaDB debe lanzarse el siguiente comando _(Substituyendo
`/path/to/database.sql` por el path de nuestro archivo `database.sql`)_:

```
source /path/to/database.sql;
```

Para salir de MariaDB debe pulsarse `Ctrl+C`.

### Backend

El backend está construido en Java 8 usando Maven como gestor de
dependencias. Para desplegar el backend deben hacerse los siguientes pasos:

Lo primero, para poder empaquetar la aplicación es necesario tener instalado
el JDK de Java 8 y Maven:

```shell
sudo apt install openjdk-8-jdk maven
```

A continuación debemos movernos a la carpeta `backend` del proyecto, donde
se encuentra nuestro archivo `pom.xml` y empaquetar la aplicación:

```shell
mvn package
```

Al hacerlo se nos generará una carpeta `target` que, entre otros archivos,
contiene el archivo `panda-learn-1.0-jar-with-dependencies.jar`.

Este archivo es un jar ejecutable que permite desplegar el servidor que
gestiona las llamadas a la API para la interacción con BBDD.

A este jar debemos pasarle como primer y único parámetro el path a un
archivo de configuración con una estructura como la del siguiente ejemplo:

```json
{
  "port": 8080,
  "databaseConfig": {
    "url": "jdbc:mariadb://localhost:3306/pandalearn",
    "user": "root",
    "password": "toor"
  }
}
```

En esta configuración tenemos los siguientes datos a cubrir:

- `port`: Puerto en el que se va a desplegar el backend.
- `databaseConfig.url`: Url a la BBDD, debe usar como driver `mariadb` y el
  nombre de la BBDD debe ser `pandalearn`. En el ejemplo se proporciona la
  url de la BBDD que esta montada en el mismo host que el backend y usa el 
  puerto por defecto de MariaDB.
- `databaseConfig.user`: Usuario de la BBDD.
- `databaseConfig.password`: Contraseña del usuario de la BBDD.

### Frontend

El frontend está construido usando Angular.

Para empaquetar el frontend necesitamos instalar NodeJS y npm:

```shell
sudo apt install nodejs npm
```

A continuación instalamos Angular CLI:

```shell
sudo npm install -g @angular/cli
```

Nos movemos a la carpeta `frontend` del proyecto y para transpilar la
aplicación ejecutamos:

```shell
npm install
ng build
```

Esto generará una carpeta `frontend/dist/frontend` que contiene los archivos
necesarios para el funcionamiento del frontend.

## Despliegue de la aplicación

Una vez desplegadas las tres partes de la aplicación tendremos lo siguiente:

- Una BBDD
- Un jar que nos permite interactuar a través de una API con BBDD
- Una carpeta que contiene los archivos de frontend

Todas las llamadas a la api se lanzan con el prefijo `/api/`. Debe montarse
el servidor de tal forma que si se hace una petición a `/api/` se redirija
la petición a nuestro backend y en cualquier otro caso sirvan archivos de
forma estática.

Hay varias opciones para lograr esto, entre las más conocidas están Apache y
Nginx. Para este ejemplo usaremos el segundo.

Instalamos nginx:

```shell
sudo apt install nginx
```

Iniciamos el servicio de nginx:

```shell
sudo systemctl start nginx
```

Como hemos hecho con MariaDB, es conveniente configurar nginx para que se
inicie cuando el sistema operativo haga boot:

```shell
sudo systemctl enable nginx
```

A continuación creamos el archivo de configuración de `nginx` en
`/etc/nginx/sites-available/pandalearn` con la siguiente configuración:

```
server {
  listen 80;
  server_name server_name;
  root /path/a/frontend;
  index index.html;
  location / {
    try_files $uri $uri/ /index.html;
  }
  location /api/ {
    proxy_pass http://localhost:8080;
  }
}
```

- `listen`: Nos sirve para hacer que el servidor escuche peticiones a un 
  puerto concreto, usamos el puerto 80 porque es el estándar para las 
  peticiones HTTP.
- `server_name`: Aquí debemos poner el nombre del servidor, por ejemplo: 
  `"pandalearn.com"`.
- `root`: Aquí se debe configurar el path a la carpeta que contiene los 
  archivos de frontend. Debe ser el path a la carpeta a la que nos referimos 
  anteriormente como `frontend/dist/frontend`.
- `proxy_pass`: Aquí debemos configurar el path de nuestro backend, en el 
  ejemplo mostrado presuponemos que se ha configurado para que se despliegue 
  en el puerto `8080`.

Y para hacer efectiva esta configuración crearemos un symlink y 
reiniciamos el servicio de la siguiente forma:

```shell
sudo ln -s /etc/nginx/sites-available/pandalearn /etc/nginx/sites-enabled/pandalearn
sudo systemctl restart nginx
```
Ahora utilizaremos crontab para autolanzar nuestro backend cada vez que se 
inicie el servidor. Escribimos:

```shell
sudo crontab -e
```

Y añadimos la siguiente línea:

```
@reboot /usr/bin/java -jar /path/al/jar/backend /path/a/config.json
```

Debemos substituir `/path/al/jar/backend` por el path al jar generado por 
maven que mencionamos anteriormente y `/path/a/config.json` por el json de 
configuración.