<?php
//getting the database connection
require_once 'conn.php';
$nombre=$_POST['nombre'];
$apellidos=$_POST['apellidos'];
$correo=$_POST['correo'];
$telefono=$_POST['telefono'];
$descripcion=$_POST['descripcion'];
$usuario=$_POST['usuario'];
$update="UPDATE usuario SET  nombre='".$nombre."', apellidos='".$apellidos."', telefono ='".$telefono."',  correo ='".$correo."', descripcion ='".$descripcion."' WHERE correo ='".$usuario."'";

mysqli_query($conn,$update) or die (mysqli_error());
mysqli_close($conn);


?>