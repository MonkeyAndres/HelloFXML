/*
 * Modelo de USUARIO
 */
package hellofxml;

/**
 *
 * @author MonkeyAndres
 */
public class Usuario {
	private String nombre;
	private int edad;
	private String ocupacion;

	public Usuario(String nombre, int edad, String ocupacion) {
		this.nombre = nombre;
		this.edad = edad;
		this.ocupacion = ocupacion;
	}

	public Usuario() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	@Override
	public String toString() {
		return "Usuario{" + "nombre=" + nombre + ", edad=" + edad + ", ocupacion=" + ocupacion + '}';
	}
}
