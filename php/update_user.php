<?php
//getting the database connection
require_once 'conn.php';
$dni=$_POST['dni'];
$nombre=$_POST['nombre'];
$apellidos=$_POST['apellidos'];
$correo=$_POST['correo'];
$nivel_juego=$_POST['nivel_juego'];
$preferencia=$_POST['preferencia'];
$telefono=$_POST['telefono'];
$genero=$_POST['genero'];
$fecha_nacimiento=$_POST['fecha_nacimiento'];
$descripcion=$_POST['descripcion'];
$direccion=$_POST['direccion'];
$contraseña=$_POST['contraseña'];
$usuario=$_POST['usuario'];
$update="UPDATE usuario SET  dni='".$dni."', nombre='".$nombre."', apellidos='".$apellidos."', direccion='".$direccion."', telefono ='".$telefono."', genero ='".$genero."', fecha_nacimiento ='".$fecha_nacimiento."',  correo ='".$correo."', nivel_juego ='".$nivel_juego."', preferencia ='".$preferencia."', descripcion ='".$descripcion."', contraseña ='".$contraseña."' WHERE correo ='".$usuario."'";
//
mysqli_query($conn,$update) or die (mysqli_error());
mysqli_close($conn);
?>