/*
ISGI Practica 5
Vladyslav Mazurkevych, 2020 (v0.1)
Esqueleto basico de un programa en OpenGL ampliado para la practica 5
Dependencias:
+GLUT

Ejercicio:
===========================================================================================================
Se quiere construir un reloj 3D analógico. Para ello se usará la
geometría de la práctica anterior y cualquier otra que el alumno construya.
El reloj debe marcar la hora local actual de forma reconocible.
Los requisitos del proyecto son los siguientes:
<----
1. Título: 'Reloj 3D'			    PDF anterior al 23-Nov
2. Titulo: 'Reloj analogico'		PDF posterior al 23-Nov
----->
2. Dimensiones del área de dibujo: cualesquiera. El usuario puede variar el tamaño del área de
dibujo estirándola con el ratón.
3. La cámara es perspectiva y el punto de vista puede estar situado en cualquier punto fuera de la
esfera unidad mirando siempre al origen. La vertical de la cámara es el eje Y.
4. Color de fondo: RGB (1.0, 1.0, 1.0)
5. Todo el reloj debe caber en la esfera unidad
6. Independientemente de la posición de la cámara y el tamaño del área de dibujo, la
transformación de la proyección debe ser tal que la esfera unidad toque los límites superior e
inferior del área de dibujo manteniendo la isometría. Mismo requisito que en P4.
7. Al menos deben percibirse los siguientes movimientos o cambios de forma, escala, color,
orientación y/o posición:
a. Un objeto debe moverse continuamente
b. Un objeto debe cambiar cada segundo
c. Un objeto debe cambiar cada minuto
d. Un objeto debe cambiar cada hora
8. Debe estar activo el z-buffer (visibilidad)
9. La animación debe ser temporalmente coherente, es decir, independiente de la velocidad del
procesador o de la carga del sistema.
10. Se debe usar doble buffer.
===========================================================================================================*/

// Caracteristicas de la ventana para el primer ejercicio
#define TITULO_VENTANA "Reloj analogico"	// "Reloj 3D"
#define DIM_X 400
#define DIM_Y 400
#define E_DIM 0.3							// Dimension de los triangulos exteriores
#define I_DIM 0.2							// Dimension de los triangulos interiores 
#define FOVY 41								// La inclinación de la perspectiva
#define PI 3.1415926
static bool MOSTRAR_ESFERA = true;			// Ponerlo a false si no se quiere mostrarla


#include <iostream>                 
#include <freeglut.h>
#include <ctime>
#include <cmath>
#include <time.h>

static GLuint estrella;
static GLuint cuadrado;
static GLuint cuadrado_interior; 
static GLuint triangulo_min;
static GLuint minutos;
static GLuint horas;

static const int tasaFPS = 40; 
static int t_segundos, t_minutos, t_horas, t_value, angulo = 0, angulo_interno = 0;
static double r_color= 0, constante = 1, posicion= 0, reduccion= 1, r= 0;
static time_t timer;
static bool direccion = true;

float rotDeg(float degree);

void init()
// Funcion de inicializacion propia
{
	//	==========================================================================================================
	//	DEFINICIONES DE LOS ELEMENTOS
	//  ==========================================================================================================
	estrella = glGenLists(1);
		glNewList(estrella, GL_COMPILE);
		glPolygonMode(GL_FRONT, GL_FILL);
		glLineWidth(2.0);
		glBegin(GL_TRIANGLE_STRIP);
		for (int i = 0; i < 4; i++)
		{
			glVertex3f(E_DIM * cos(i * 2 * PI / 3 + rotDeg(90)), E_DIM * sin(i * 2 * PI / 3 + rotDeg(90)), 0.0);
			glVertex3f(I_DIM * cos(i * 2 * PI / 3 + rotDeg(90)), I_DIM * sin(i * 2 * PI / 3 + rotDeg(90)), 0.0);
		}
		glEnd();

		glBegin(GL_TRIANGLE_STRIP);
		for (int i = 0; i < 4; i++)
		{
			glVertex3f(E_DIM * cos(i * 2 * PI / 3 + rotDeg(-90)), E_DIM * sin(i * 2 * PI / 3 + rotDeg(-90)), 0.0);
			glVertex3f(I_DIM * cos(i * 2 * PI / 3 + rotDeg(-90)), I_DIM * sin(i * 2 * PI / 3 + rotDeg(-90)), 0.0);
		}
		glEnd();
	glEndList();

	// Declarando el cuadrado de las horas y minutos
	cuadrado = glGenLists(2);
		glNewList(cuadrado, GL_COMPILE);
		glPolygonMode(GL_FRONT, GL_FILL);
		glLineWidth(1.0);
		glBegin(GL_TRIANGLE_STRIP);
			glVertex3f(-0.03, 0, 0.0);
			glVertex3f(-0.03, 0, 0.1);

			glVertex3f(0.03, 0, 0.0);
			glVertex3f(0.03, 0, 0.1);

			glVertex3f(0.03, 0.2, 0.0);
			glVertex3f(0.03, 0.2, 0.1);

			glVertex3f(-0.03, 0.2, 0.0);
			glVertex3f(-0.03, 0.2, 0.1);

			glVertex3f(-0.03, 0, 0.0);
			glVertex3f(-0.03, 0, 0.1);
		glEnd();
	glEndList();

	// Declarando el rectangulo interior de las horas
	cuadrado_interior = glGenLists(2);
	glNewList(cuadrado_interior, GL_COMPILE);
		glPolygonMode(GL_FRONT, GL_FILL);
		glLineWidth(1.0);
		glBegin(GL_TRIANGLE_STRIP);
			glVertex3f(-0.03, 0, 0.05);
			glVertex3f(0.03, 0, 0.05);
			glVertex3f(0.03, 0.2, 0.05);
			glVertex3f(-0.03, 0.2, 0.05);
			glVertex3f(-0.03, 0, 0.05);
		glEnd();
	glEndList();

	// Declarando el triangulo de minutos entre horas
	triangulo_min = glGenLists(2);
	glNewList(triangulo_min, GL_COMPILE);
		glPolygonMode(GL_FRONT, GL_FILL);
		glLineWidth(1.0);
		float base = 0.02;
		glBegin(GL_TRIANGLE_STRIP);
			glVertex3f(-base, 0.15, -base);
			glVertex3f(base, 0.15, -base);
			glVertex3f(base, 0.15, base);
			glVertex3f(-base, 0.15, base);
			glVertex3f(-base, 0.15, -base);
			glColor3f(1, 0, 1);
			glVertex3f(0, 0.05, 0);
			glColor3f(0, 0, 0);
			glVertex3f(-base, 0.15, base);
			glVertex3f(base, 0.15, base);
			glColor3f(1, 0, 1);
			glVertex3f(0, 0.05, 0);
			glColor3f(0, 0, 0);
			glVertex3f(base, 0.15, -base);
		glEnd();
	glEndList();

	// Triangulo horas
	minutos = glGenLists(2);
	glNewList(minutos, GL_COMPILE);
		glPolygonMode(GL_FRONT, GL_FILL);
		glLineWidth(1.0);
		base = 0.045;
		float altura_base = 0.2;
		glBegin(GL_TRIANGLE_STRIP);
			glVertex3f(-base, -altura_base, 0);
			glVertex3f(base, -altura_base, 0);
			glVertex3f(base, -altura_base, base);
			glVertex3f(-base, -altura_base, base);
			glVertex3f(-base, -altura_base, 0);
			glColor3f(1, 0, 0);
			glVertex3f(0, 0.2, 0);
			glColor3f(0, 0, 0);
			glVertex3f(-base, -altura_base, base);
			glVertex3f(base, -altura_base, base);
			glColor3f(1, 0, 0);
			glVertex3f(0, 0.2, 0);
			glColor3f(0, 0, 0);
			glVertex3f(base, -altura_base, 0);
		glEnd();
	glEndList();

	// Flecha de las horas
	horas = glGenLists(2);
	glNewList(horas, GL_COMPILE);
		glPolygonMode(GL_FRONT, GL_FILL);
		glLineWidth(1.0);
		base = 0.05;
		altura_base = 0.2;
		glBegin(GL_TRIANGLE_STRIP);
			glVertex3f(-base, -altura_base, -base);
			glVertex3f(base, -altura_base, -base);
			glVertex3f(base, -altura_base, 0);
			glVertex3f(-base, -altura_base, 0);
			glVertex3f(-base, -altura_base, -base);
			glColor3f(1, 0, 0);
			glVertex3f(0, 0.1, 0);
			glColor3f(0, 0, 0);
			glVertex3f(-base, -altura_base, 0);
			glVertex3f(base, -altura_base, 0);
			glColor3f(1, 0, 0);
			glVertex3f(0, 0.1, 0);
			glColor3f(0, 0, 0);
			glVertex3f(base, -altura_base, -base);
		glEnd();
	glEndList();
}

float rotDeg(float degree)
{
	// Dado un angulo, devuelve su radian con signo opuesto para que sea mas humano
	return -degree * PI / 180;
}

void display()
// Funcion de atencion al dibujo
{
	glClearColor(1.0, 1.0, 1.0, 0);							//Declarando el color de fondo
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);     // Buffer de color y el z-buffer

	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();

	gluLookAt(0.5, 2, 2, 0, 0, 0, 0, 1, 0);					// Perfil estrella
	//gluLookAt(0, 0, 3, 0, 0, 0, 0, 1, 0);					// Perfil frente 2D

	glColor3f(0.9, 0.8, 0.9);
	if(MOSTRAR_ESFERA)
		glutWireSphere(1.0, 20, 20);

	glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

	// Estrella
	for (int i = 0; i < 2; i++) {
		glPushMatrix();
		glEnable(GL_DEPTH_TEST);
		glColor3f(0.5, 0.01, 0.01);
		glRotatef(i * 90, 0, 1, 0);
		glRotatef(angulo, 0, 1, 0);
		glCallList(estrella);
		glDisable(GL_DEPTH_TEST);
		glPopMatrix();
	}

	// Esfera del centro
	glPushMatrix();
	glEnable(GL_DEPTH_TEST);
	glColor3f(1, 1, 0);
	glutWireSphere(0.01,20,20);
	glDisable(GL_DEPTH_TEST);
	glPopMatrix();

	// Arco de la esfera
	for(int i = 0; i < 360; i++) {
		glPushMatrix();
		glEnable(GL_DEPTH_TEST);
		glColor3f(0.15, 0, 0.15);
		glRotatef(i, 0, 1, 0);
		glTranslatef(0.15, 0, 0);
		glutWireSphere(0.004, 20, 20);
		glDisable(GL_DEPTH_TEST);
		glPopMatrix();
	}

	// Satelites exteriores (EXTRA,)
	for (int i = 0; i < 360; i++) {
		glPushMatrix();
		glEnable(GL_DEPTH_TEST);
		glColor3f(0, 0, 0.2);
		glRotatef(45+angulo_interno, 1, 0, 0);
		glRotatef(i+ angulo, 0, 1, 0);
		glTranslatef(0.95, 0, 0);
		if (i % 120 == 0) {
			glColor3f(0.6,0, 0);
			glutWireSphere(0.008, 20, 20);
		}
		else {
			glutWireSphere(0.001, 20, 20);
		}
		glDisable(GL_DEPTH_TEST);
		glPopMatrix();
	}

	// Arco de los segundos
	for (int i = 0; i < 360; i++) {
		glPushMatrix();
		glEnable(GL_DEPTH_TEST);
		glColor3f(r_color, 0, 1);
		glRotatef(90, 1, 0, 0);
		glRotatef(i+ angulo, 0, 1, 0);
		glTranslatef(0.97, 0, 0);
		glutWireSphere(0.002, 20, 20);
		glDisable(GL_DEPTH_TEST);
		glPopMatrix();
	}
	// Arco de la esfera exterior
	for (int i = 0; i < 60; i++) {
		glPushMatrix();
		glEnable(GL_DEPTH_TEST);
		glColor3f(r_color, 0.2, 1);
		glRotatef(6*i + angulo_interno, 0, 1, 0);
		glTranslatef(0.25, 0, 0);
		glutWireSphere(0.004, 20, 20);
		glDisable(GL_DEPTH_TEST);
		glPopMatrix();
	}

	// Estrella del centro
	for (int i = 0; i < 6; i++) {
		glPushMatrix();
		glEnable(GL_DEPTH_TEST);
		glColor3f(0.15 * i, 0, 0.15 * i);
		glRotatef(i * 30, 0, 1, 0);
		glRotatef(angulo_interno, 0, 1, 0);
		glScalef(0.3, 0.3, 0.3);
		glCallList(estrella);
		glDisable(GL_DEPTH_TEST);
		glPopMatrix();
	}

	// Rectangulos de las horas
	for (int ang = 0; ang < 12; ang++) {
		glPushMatrix();
		glEnable(GL_DEPTH_TEST);
		glColor3f(0, 0, 0);
		glRotatef(30 * ang, 0, 0, 1);
		glTranslatef(0, 0.7, 0);
		glCallList(cuadrado);
		glDisable(GL_DEPTH_TEST);
		glPopMatrix();

		glPushMatrix();
		glEnable(GL_DEPTH_TEST);
		glColor3f(1, 0, 0);
		glRotatef(30 * ang, 0, 0, 1);
		glTranslatef(0, 0.7, 0);
		glCallList(cuadrado_interior);
		glDisable(GL_DEPTH_TEST);
		glPopMatrix();
	}

	// Triangulo de los minutos
	for (int i = 0; i < 60; i++) {
		if (i % 5 != 0) {
			glPushMatrix();
			glEnable(GL_DEPTH_TEST);
			glColor3f(0, 0, 0);
			glRotatef(6 * i, 0, 0, 1);
			glRotatef(-angulo, 0, 0.7, 0.0);
			glTranslatef(0, 0.7+ posicion, 0.0);
			glCallList(triangulo_min);
			glDisable(GL_DEPTH_TEST);
			glPopMatrix();
		}
	}

	// Horas
	glPushMatrix();
	glEnable(GL_DEPTH_TEST);
	glColor3f(0, 0, 0);
	glRotatef(-30*t_horas, 0, 0, 1);
	glTranslatef(0, 0.51, -0.01);
	glCallList(horas);
	glDisable(GL_DEPTH_TEST);
	glPopMatrix();

	// Minutos
	glPushMatrix();
	glEnable(GL_DEPTH_TEST);
	glColor3f(0, 0, 0);
	glRotatef(-6*t_minutos, 0, 0, 1);
	glTranslatef(0, 0.51, 0.01);
	glCallList(minutos);
	glDisable(GL_DEPTH_TEST);
	glPopMatrix();

	// Segundos
	glPushMatrix();
	glEnable(GL_DEPTH_TEST);
	glColor3f(0, 0, 0);
	glRotatef(-6*t_segundos + 90, 0, 0, 1);
	glTranslatef(0.97, -0.1, 0);
	glCallList(triangulo_min);
	glRotatef(180, 0, 0, 1);
	glTranslatef(0, -0.22, 0);
	glCallList(triangulo_min);
	glRotatef(-90, 0, 0, 1);
	glTranslatef(-0.11,0.01,0);
	glScalef(0.2, 0.2, 0.2);
	glCallList(minutos);
	glDisable(GL_DEPTH_TEST);
	glPopMatrix();

	glutSwapBuffers(); //Internamente ya hace glFlush()
}
void reshape(GLint w, GLint h)
// Funcion de atencion al redimensionamiento
{
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glViewport(0, 0, w, h);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	float razon = (float)w / h;

	gluPerspective(FOVY, razon, 1, 10);
}

void onTimer(int valor){
	static int antes = 0;
	int ahora, tiempo_transcurrido;

	ahora = glutGet(GLUT_ELAPSED_TIME);
	tiempo_transcurrido = ahora - antes;

	//----------------------------------------------------------------
	// Obtengo el tiempo actual
	time(&timer);
	struct tm y2k = { 0 };
	y2k.tm_hour = 0;   y2k.tm_min = 0; y2k.tm_sec = 0;
	y2k.tm_year = 100; y2k.tm_mon = 0; y2k.tm_mday = 1;
	time(&timer); 
	t_value = difftime(timer, mktime(&y2k));
	//----------------------------------------------------------------
	//b.Un objeto debe cambiar cada segundo
	t_segundos = t_value % 60;
	//c.Un objeto debe cambiar cada minuto
	t_minutos = (t_value % 3600) / 60;
	//d.Un objeto debe cambiar cada hora
	t_horas = (t_value / 3600) % 12;
	//a.Un objeto debe moverse continuamente
	angulo = (angulo + 2) % 360;

	if (angulo % 2 == 0)
		angulo_interno = (angulo_interno - 1) % 360;

	// Cambio de posicion
	if (posicion > 0.025) {
		direccion = false;
	}
	else if (posicion < -0.025) {
		direccion = true;
	}
	if (direccion) {
		if (r > reduccion) {
			posicion += 0.001;
			r = 0;
		}
		r++;
	}else {
		if (r > reduccion) {
			posicion -= 0.001;
			r = 0;
		}
		r++;
	}

	printf("%.d horas y %.d minutos y %.d segundos,  %.d grados\n", t_horas, t_minutos, t_segundos, angulo);

	glutTimerFunc(1000 / tasaFPS, onTimer, tasaFPS);
	glutPostRedisplay();
}

void onIdle() {
	// Cambio de color
	if(r_color >= 1) {
		r_color = 0;
	}
	r_color = r_color + 0.00001;
}

int main(int argc, char** argv)
// Programa principal
{
	glutInit(&argc, argv);											// Inicializacion de GLUT
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);		// Alta de buffers a usar
	glutInitWindowSize(DIM_X, DIM_Y);								// Tamanyo inicial de la ventana
	glutInitWindowPosition(50, 600);
	glutCreateWindow(TITULO_VENTANA);								// Creacion de la ventana con su titulo
	glutIdleFunc(onIdle);											// Cambios internos
	glutTimerFunc(1000 / tasaFPS, onTimer, tasaFPS);				// Alta de la funcion de atencion al timer
	init();

	std::cout << TITULO_VENTANA << " running" << std::endl;			// Mensaje por consola
	glutDisplayFunc(display);										// Alta de la funcion de atencion a display
	glutReshapeFunc(reshape);										// Alta de la funcion de atencion a reshape
	glutMainLoop();													// Puesta en marcha del programa
}
