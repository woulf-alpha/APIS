# APIS
Mis APIS


## SSPREFRENCES
Nos permite agilizar la lectura, escritura, modificación del SharedPrefered de Android.

### Caracteristicas:
* [Crear](https://github.com/woulf-alpha/APIS/blob/master/README.md#constructor)
* [Leer](https://github.com/woulf-alpha/APIS/blob/master/README.md#leer)
* [Escribir](https://github.com/woulf-alpha/APIS/blob/master/README.md#escribir)
* [Copiar](https://github.com/woulf-alpha/APIS/blob/master/README.md#copiar)
* [Eliminar](https://github.com/woulf-alpha/APIS/blob/master/README.md#eliminar)


##### CONSTRUCTOR
___

El constructor puede recibir uno o dos parametros. 
En el primer caso la clase se encargará de crear el archivo con un nombre "aleatorio".
En el segundo caso se le expcifica que el nombre del archivo va a ser "Saludos".

``` java
 ss = new SSPreferences(this); 
 
 ss = new SSPreferences(this, "Saludos");
```

##### LECTURA
___

``` java
  Map<?, ?> mapa = ss.leer();
 
  Map<?, ?> mapa = ss.leer("Saludos");
```

##### ESCRITURA
___

``` java
  
  ss.write(String key, String value),
      ss.write("0", "valor");
  ss.write(String f_out, String key, String value);
      ss.write("Saludos", "1", "valor");
```

##### COPIAR
___

``` java
  ss.copy(String f_in, String f_out);
    ss.copy(R.asses..., "Saludos")
  
```

##### ELIMINAR
___

``` java
  ss.remove(String key)
     ss.remove("0")
  ss.remove(String f_out, String key)
    ss.remove("Saludos", "0")
  ss.removeAll();
```
