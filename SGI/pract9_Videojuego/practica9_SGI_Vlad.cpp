/*
ISGI Practica 9
Vladyslav Mazurkevych, 2020 (v0.1)
Esqueleto basico de un programa en OpenGL ampliado para la practica 5
Dependencias:
+GLUT

Ejercicio:
===========================================================================================================
Esta práctica es la recopilación de todas las técnicas vistas en el curso. Para completar el videojuego, se
quiere añadir efectos de medio participante (niebla, humo o polvo por ejemplo) a la práctica 8, además
de algunos elementos solidarios al vehículo como indicadores, visión del capó, estructura del parabrisas,
escobillas, etc. Se valorará también la inclusión de indicadores traslúcidos (como sobre impresos en el
parabrisas) que no molesten la visión de la carretera.
La práctica debe recopilar el trabajo de las anteriores. Para conseguir la máxima puntuación de
4 puntos el videojuego debe responder a la interacción siguiente, valorándose en cada
caso la dificultad y riqueza de la escena (geometría del circuito, texturas, luces, elementos
adicionales, fondo panorámico, animación del entorno, etc.) así como mejoras en la interacción y
la lógica de la aplicación (cambio de cámara, interacción entre objetos, modos de juego, otros
movimientos, etc.):
1. Flecha izquierda/derecha: giro del vehículo
2. Flecha arriba/abajo: aumento/disminución de la velocidad
3. S/s: Activa/desactiva un modelo simple en alámbrico de la práctica 6 sin luces ni texturas
4. L/l: Cambia entre modo diurno/nocturno
5. N/n: Cambia el estado de la niebla (on/off)
6. C/c: Cambia la visibilidad de elementos solidarios a la cámara -HUD- (on/off)
Por consola debe indicarse al usuario la forma de interactuar.
Se pide construir un proyecto bajo el entorno de Visual Studio C++ cuyo código fuente y ejecutable
cumplan con los requisitos anteriores y cualquier otro adicional que el alumno tenga a bien incluir. La
entrega se realizará según la normativa para la entrega de prácticas publicada en el portal de la
asignatura. Además:
• Deben adjuntarse las texturas utilizadas que se cargarán sin problemas cuando residan en el
mismo directorio que el ejecutable.
• Debe adjuntarse una captura de pantalla del circuito visto desde arriba y con los ejes de la escena
dibujados
===========================================================================================================*/
#include <iostream>        
#include <freeglut.h>
#include <ctime>
#include <cmath>
#include <sstream>
#include <time.h>
#include <../Utilidades.h>			// Mis archivos estaban distintos, asi que los tuve que cambiar
#include <../freeimage/FreeImage.h> 

// Caracteristicas de la ventana para el primer ejercicio
#define TITULO_VENTANA "Interfaz de conducción"	// "Reloj 3D"
#define DIM_X 900
#define DIM_Y 600
#define E_DIM 0.3							// Dimension de los triangulos exteriores
#define I_DIM 0.2							// Dimension de los triangulos interiores 
#define FOVY 45								// La inclinación de la perspectiva
#define PI 3.1415926
#define FPS 50                              // Tasa de los FPS

using namespace std;

// ===========================================================================================
// Datos generales
// ===========================================================================================
// Camera
static const float INCREMENTO_VELOCIDAD = 0.1;	// Pedido en el ejercicio 0.1
static const float INCREMENTO_ALPHA = 1; // Pedido en el ejercicio 0.25
static const float ALTURA_FAROLA = 4;  // Pedido en el ejercicio de farolas
static const float DIR_FARO_INI = -0.7; // Pedido en el ejercicio -0.7, pero -0.9 se ve mejor
static float DIR_FARO = DIR_FARO_INI;
static bool MIRAR_ARRIBA = false; // Simula la activacion de los faros del techo de un coche

// Extra
static bool LUCES_LARGAS = false; // Estando a False es como la practica pide, en true es como se ve bien

static const float TOPE_VELOCIDAD = -1; // se recomienda 10
static const float TOPE_ALPHA = 80;
static const float DENSIDAD_NIEBLA = 0.07;
static const float ALTURA_CIELO = 50;
static float DISTANCIA_PLASMA = 3; // Distancia a la que hay que estar de la plasma para que cuente la puntuacion, de vez en cuando, hay un bonus y da mas de una plasma

static float desplazamiento = 0.0;
static float velocidad = 0.0;
static float posX = 0, posZ = 0, posAX= 0, posAZ= 0;
static float despX = 0.296, despZ = 0.95; //desplazamiento de la camera
static float alpha = 7.5;
static float alpha_volante = 0;

static float ticks = 0;
static const float tick = 1;
static float coef[16];


// Juego extra
static int plasma = 0;
static bool contar1 = true;
static bool contar2 = true;
static bool contar3 = true;
static bool contar4 = true;

// Texturas
static GLuint carretera, cielo, alpes, banner1, banner2, banner3, banner4, suelo, metal, capo, covid_t, desierto;

// Material pedido para la carretera
static GLfloat carretera_dif[] = { 0.8,0.8,0.8 };
static GLfloat carretera_esp[] = { 0.3,0.3,0.3 };

static GLuint rectangulo, flecha, luces;
static int g = 0;

// ===========================================================================================
// Circuito 
// ===========================================================================================
static float A = 10;
static float T = 200;
static bool ini = true;

// ===========================================================================================
// Funcionalidades pedidas
// ===========================================================================================
static enum { ALAMBRICO, SOLIDO } modo_dibujo;
static enum { DIURNO, NOCTURNO } modo_dia;
static enum { ON, OFF } modo_niebla, modo_hud;
static enum { FOV, PERSONALIZADO } modo_fondo;


// Modo Alambrico o Solido
void dibujar_modo_dibujo() {
	/*Dibuja la escena acorde al modo seleccionado*/
	if (modo_dibujo == ALAMBRICO) {
		glPushMatrix();
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glDisable(GL_LIGHTING);
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();
	}
	else {
		glPushMatrix();
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glEnable(GL_LIGHTING);
		glEnable(GL_TEXTURE_2D);
		glPopMatrix();
	}
}

// Noche o Dia
void dibujar_modo_dia() {
	if (modo_dia == NOCTURNO) {

		glPushMatrix();
		glEnable(GL_LIGHTING);
		glShadeModel(GL_SMOOTH);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glColorMaterial(GL_FRONT, GL_DIFFUSE);

		glPopMatrix();
	}
	else {
		glClearColor(1, 1, 1, 1);
		glPushMatrix();
		glDisable(GL_LIGHTING);
		glDisable(GL_BLEND);
		glPopMatrix();
	}
}

// Modo de Niebla ON - OFF
void dibujar_modo_niebla() {
	if (modo_niebla == ON) {
		glPushMatrix();
		glEnable(GL_FOG);
		GLfloat tono[] = { 0.21, 0.21, 0.21 };
		glFogfv(GL_FOG_COLOR, tono);
		glFogf(GL_FOG_DENSITY, DENSIDAD_NIEBLA);
		glPopMatrix();
	}
	else {
		glDisable(GL_FOG);
	}
}

// Speed Bar
void dibujar_barra_velocidad() {

	glDisable(GL_TEXTURE_2D);
	glDisable(GL_LIGHTING);
	glPushMatrix();
	glColor3f(0.0, 1.0, 0.0);

	glBegin(GL_POLYGON);

	if (velocidad >= 2.0) {
		glColor3f(0.0, 0.9, 0.0);
	}
	if (velocidad >= 5.5) {
		glColor3f(0.9, 0.9, 0.0);
	}
	if (velocidad >= 7) {
		glColor3f(1.0, 0, 0.0);
	}

	glTranslatef(-0.5, -5, 0);
	float x1 = -1.15;
	glVertex3f(x1, -0.8, -2.0);
	glVertex3f(x1, -0.78, -2.0);

	if (velocidad >= 4.0) {
		glColor3f(1.0, 0.5, 0.0);
	}
	if (velocidad >= 6.0) {
		glColor3f(0.7, 0.0, 0.0);
	}
	if (velocidad >= 8) {
		glColor3f(0.8, 0, 0);
	}
	if (velocidad <= 10)
		glVertex3f(x1 - (x1 - 0.5) * velocidad / (24 + 0.0001), -0.78 + (velocidad / 100), -2.0);
	else
		glVertex3f(x1 - (x1 - 0.5) * 10 / (24 + 0.0001), -0.78 + (velocidad / 100), -2.0);

	if (velocidad > 10) {
		glColor3f(0.8, 0, 0.9);
	}
	if (velocidad <= 10)
		glVertex3f(x1 - (x1 - 0.5) * velocidad / (24 + 0.00001), -0.8, -2.0);
	else
		glVertex3f(x1 - (x1 - 0.5) * 10 / (24 + 0.00001), -0.8, -2.0);

	glEnd();

	glColor3f(1, 1, 1);
	glPopMatrix();

	// Brujula
	glPushMatrix();
	glTranslatef(0.4, -0.6, 0);
	glRotatef(-alpha, 0, 0, 1);
	glColor3f(1, 0,0);
	glCallList(flecha);
	glPopMatrix();

	if (LUCES_LARGAS == true) {
		// Muestra que estan anctivas
		g = 0;
	}
	else {
		g = 90;
	}
	// Simbolo de las luces largas del coche
	glPushMatrix();
	glTranslatef(0, -0.56, 0);
	glRotatef(g, 0, 1, 0);
	glColor3f(0, 0, 1);
	glCallList(luces);
	glPopMatrix();
}

// ===========================================================================================
// Funciones auxiliares
// ===========================================================================================
float trazado(float u, float A, float T) {
	return A * sin(u * ((2 * PI) / T));
}

void iluminacion() {
	// ==============================================
	// Activamos las luces y sombras
	// ==============================================
	glClearColor(0, 0, 0, 1);
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);
	glEnable(GL_LIGHT1);
	glEnable(GL_LIGHT2);
	glEnable(GL_LIGHT3);
	glEnable(GL_LIGHT4);
	glEnable(GL_LIGHT5);

	//glEnable(GL_TEXTURE_2D);
	glShadeModel(GL_SMOOTH); // Activo sombras

	// ==============================================
	// Iluminacion direcciona de la luna
	// ==============================================
	GLfloat A0[] = { 0.05, 0.05, 0.05 ,1.0 };
	GLfloat D0[] = { 0.05, 0.05, 0.05 ,1.0 };
	GLfloat S0[] = { 0.0,0.0,0.0 ,1.0 };

	glLightfv(GL_LIGHT0, GL_AMBIENT, A0);
	glLightfv(GL_LIGHT0, GL_DIFFUSE, D0);
	glLightfv(GL_LIGHT0, GL_SPECULAR, S0);


	// ==============================================
	// Iluminacion focal de un faro al vehiculo
	// ==============================================
	glLightfv(GL_LIGHT1, GL_AMBIENT, BLANCO);
	glLightfv(GL_LIGHT1, GL_DIFFUSE, AMARILLO);
	glLightfv(GL_LIGHT1, GL_SPECULAR, BLANCO);
	if (LUCES_LARGAS) {
		// Version que funciona y se notan las luces
		DIR_FARO = -1.0;
		glLightfv(GL_LIGHT1, GL_AMBIENT, BLANCO); 
		glLightfv(GL_LIGHT1, GL_DIFFUSE, BLANCO);
		glLightfv(GL_LIGHT1, GL_SPECULAR, BLANCO); 
	}
	else {
		// Version que salia en la practica, Es muy leve la luz, asi que pase a usar todo blanco
		/*GLfloat A1[] = { 0.2, 0.2, 0.2, 1.0 };
		GLfloat D1[] = { 1.0,1.0,1.0, 1.0 };
		GLfloat S1[] = { 0.3,0.3,0.3,1.0 };

		glLightfv(GL_LIGHT1, GL_AMBIENT, A1);
		glLightfv(GL_LIGHT1, GL_DIFFUSE, D1);
		glLightfv(GL_LIGHT1, GL_SPECULAR, S1);*/
		DIR_FARO = DIR_FARO_INI;
	}

	GLfloat dir_central[] = { 0.0, -1.0, 0.0 };
	glLightfv(GL_LIGHT2, GL_SPOT_DIRECTION, dir_central);
	glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 25);
	glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 20.0);


	// ==============================================
	// Farola 1
	// ==============================================
	GLfloat A2[] = { 0.0, 0.0, 0.0, 1.0 };
	GLfloat D2[] = { 0.5, 0.5, 0.2, 1.0 };
	GLfloat S2[] = { 0.0,0.0,0.0,1.0 };
	GLfloat dir_farola[] = { 0.0,-1.0,0.0 };

	glLightfv(GL_LIGHT2, GL_AMBIENT, A2);
	glLightfv(GL_LIGHT2, GL_DIFFUSE, D2);
	glLightfv(GL_LIGHT2, GL_SPECULAR, S2);
	glLightf(GL_LIGHT2, GL_SPOT_CUTOFF, 45);
	glLightf(GL_LIGHT2, GL_SPOT_EXPONENT, 10.0);
	glLightfv(GL_LIGHT2, GL_SPOT_DIRECTION, dir_farola);


	// ==============================================
	// Farola 2
	// ==============================================
	GLfloat A3[] = { 0.0, 0.0, 0.0, 1.0 };
	GLfloat D3[] = { 0.5, 0.5, 0.2, 1.0 };
	GLfloat S3[] = { 0.0,0.0,0.0,1.0 };

	glLightfv(GL_LIGHT3, GL_AMBIENT, A3);
	glLightfv(GL_LIGHT3, GL_DIFFUSE, D3);
	glLightfv(GL_LIGHT3, GL_SPECULAR, S3);
	glLightf(GL_LIGHT3, GL_SPOT_CUTOFF, 45);
	glLightf(GL_LIGHT3, GL_SPOT_EXPONENT, 10.0);
	glLightfv(GL_LIGHT3, GL_SPOT_DIRECTION, dir_farola);


	// ==============================================
	// Farola 3
	// ==============================================
	GLfloat A4[] = { 0.0, 0.0, 0.0, 1.0 };
	GLfloat D4[] = { 0.5, 0.5, 0.2, 1.0 };
	GLfloat S4[] = { 0.0,0.0,0.0,1.0 };

	glLightfv(GL_LIGHT4, GL_AMBIENT, A4);
	glLightfv(GL_LIGHT4, GL_DIFFUSE, D4);
	glLightfv(GL_LIGHT4, GL_SPECULAR, S4);
	glLightf(GL_LIGHT4, GL_SPOT_CUTOFF, 45);
	glLightf(GL_LIGHT4, GL_SPOT_EXPONENT, 10.0);
	glLightfv(GL_LIGHT4, GL_SPOT_DIRECTION, dir_farola);


	// ==============================================
	// Farola 4
	// ==============================================
	GLfloat A5[] = { 0.0, 0.0, 0.0, 1.0 };
	GLfloat D5[] = { 0.5, 0.5, 0.2, 1.0 };
	GLfloat S5[] = { 0.0, 0.0, 0.0,1.0 };

	glLightfv(GL_LIGHT5, GL_AMBIENT, A5);
	glLightfv(GL_LIGHT5, GL_DIFFUSE, D5);
	glLightfv(GL_LIGHT5, GL_SPECULAR, S5);
	glLightf(GL_LIGHT5, GL_SPOT_CUTOFF, 45);
	glLightf(GL_LIGHT5, GL_SPOT_EXPONENT, 10.0);
	glLightfv(GL_LIGHT5, GL_SPOT_DIRECTION, dir_farola);


	// ==============================================
	// Activo render
	// ==============================================
	//glEnable(GL_DEPTH_TEST);
	//glEnable(GL_LIGHTING);
}

void cargar_texturas()
// Carga todas las texturas dadas
{
	glGenTextures(1, &carretera);
	glBindTexture(GL_TEXTURE_2D, carretera);
	loadImageFile((char*)"./carretera.png");

	glGenTextures(1, &cielo);
	glBindTexture(GL_TEXTURE_2D, cielo);
	loadImageFile((char*)"./sky.png");

	glGenTextures(1, &alpes);
	glBindTexture(GL_TEXTURE_2D, alpes);
	loadImageFile((char*)"./mountain3.jpg");

	glGenTextures(1, &banner1);
	glBindTexture(GL_TEXTURE_2D, banner1);
	loadImageFile((char*)"./banner1.jpg");

	glGenTextures(1, &banner2);
	glBindTexture(GL_TEXTURE_2D, banner2);
	loadImageFile((char*)"./banner2.jpg");

	glGenTextures(1, &banner3);
	glBindTexture(GL_TEXTURE_2D, banner3);
	loadImageFile((char*)"./cocacola.jpg");

	glGenTextures(1, &banner4);
	glBindTexture(GL_TEXTURE_2D, banner4);
	loadImageFile((char*)"./ad4.png");

	glGenTextures(1, &suelo);
	glBindTexture(GL_TEXTURE_2D, suelo);
	loadImageFile((char*)"./grass.jpg");

	glGenTextures(1, &desierto);
	glBindTexture(GL_TEXTURE_2D, desierto);
	loadImageFile((char*)"./desierto.jpg");

	glGenTextures(1, &metal);
	glBindTexture(GL_TEXTURE_2D, metal);
	loadImageFile((char*)"./metal.png");

	glGenTextures(1, &capo);
	glBindTexture(GL_TEXTURE_2D, capo);
	loadImageFile((char*)"./carbon.png");

	glGenTextures(1, &covid_t);
	glBindTexture(GL_TEXTURE_2D, covid_t);
	loadImageFile((char*)"./plasma.png");
}

void onMenu(int opcion)
{
	// Callback de atencion al menu de pop-up
	if (opcion == 0)
		modo_fondo = FOV;
	else if (opcion == 1)
		modo_fondo = PERSONALIZADO;

	glutPostRedisplay();
}

void init() {
	//glEnable(GL_DEPTH_TEST);
	//glEnable(GL_TEXTURE_2D);
	//glEnable(GL_LIGHTING);

	glClearColor(1.0, 1.0, 1.0, 1.0);
	cargar_texturas();


	cout << "1. Flecha izquierda / derecha: giro del vehículo" << endl;
	cout << "2. Flecha arriba / abajo : aumento / disminución de la velocidad" << endl;
	cout << "3. S / s : Activa / desactiva un modelo simple en alámbrico de la práctica 6 sin luces ni texturas" << endl;
	cout << "4. L / l : Cambia entre modo diurno / nocturno" << endl;
	cout << "5. N / n : Cambia el estado de la niebla(on / off)" << endl;
	cout << "6. C / c : Cambia la visibilidad de elementos solidarios a la cámara - HUD - (on / off)" << endl;
	cout << "7. z : Cambiar de luces entre largas y cortas (on / off)" << endl;
	cout << "8. p : Hacer captura de pantall" << endl;
	cout << "9. a : Mirar arriba apuntando con los faros" << endl;

	// Inicio los modos
	modo_dia = DIURNO;
	modo_dibujo = SOLIDO;
	modo_niebla = OFF;
	modo_hud = ON;
	modo_fondo = PERSONALIZADO;

	// Menu de contexto
	glutCreateMenu(onMenu);
	glutAddMenuEntry("Fondo con FOV Completo", 0);
	glutAddMenuEntry("Suelo + Fondo personalizado", 1);
	glutAttachMenu(GLUT_RIGHT_BUTTON);

	float h = 0.21;
	rectangulo = glGenLists(2);
	glNewList(rectangulo, GL_COMPILE);
	glPolygonMode(GL_FRONT, GL_FILL);
	glLineWidth(1.0);
	glBegin(GL_TRIANGLE_STRIP);
	glVertex3f(-0.02, 0.09+ h, -1.45);
	glVertex3f(-0.02, 0.0 + h, -1.45);
	glVertex3f(0.02, 0.0 + h, -1.45);
	glVertex3f(0.02, 0.09 + h, -1.45);
	glVertex3f(-0.02, 0.09 + h, -1.45);
	glEnd();
	glEndList();

	flecha = glGenLists(2);
	glNewList(flecha, GL_COMPILE);
	glPolygonMode(GL_FRONT, GL_FILL);
	glLineWidth(1.0);
	glBegin(GL_TRIANGLE_STRIP);
	glVertex3f(-0.02, 0.09, -1.45);
	glVertex3f(-0.02, 0.0, -1.45);
	glVertex3f(0.02, 0.0, -1.45);
	glVertex3f(0.02, 0.09, -1.45);
	glVertex3f(-0.02, 0.09, -1.45);
	glVertex3f(-0.04, 0.09, -1.45);
	glVertex3f(0.0, 0.12, -1.45);
	glVertex3f(0.04, 0.09, -1.45);
	glVertex3f(-0.02, 0.09, -1.45);
	glEnd();
	glEndList();

	luces = glGenLists(2);
	glNewList(luces, GL_COMPILE);
	glPolygonMode(GL_FRONT, GL_FILL);
	glLineWidth(1.0);
	glBegin(GL_TRIANGLE_STRIP);
	glVertex3f(0, 0.04, -1.45);
	glVertex3f(-0.02, 0.0, -1.45);
	glVertex3f(0.02, 0.0, -1.45);
	glVertex3f(0, 0.04, -1.45);
	glEnd();
	glEndList();
}

void procesar_textura(GLuint texture) {
	glBindTexture(GL_TEXTURE_2D, texture);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_MIRRORED_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_MIRRORED_REPEAT);
}

void aplicar_textura_cielo(int area) {
	GLUquadric* quadratic;
	quadratic = gluNewQuadric();

	glPushMatrix();
	//glEnable(GL_TEXTURE_2D);
	glBindTexture(GL_TEXTURE_2D, alpes);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	gluQuadricTexture(quadratic, 1);

	glTranslatef(posX, 0, posZ);
	//glColor3f(0, 0, 1);
	glRotatef(-90, 1, 0, 0);
	gluCylinder(quadratic, 100, 100, ALTURA_CIELO, 50, 50);
	glPopMatrix();
}


void aplicar_textura_suelo(int area, int cantidad) {
	glPushMatrix();
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, BLANCO);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, BLANCO);
	glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 5);
	glBindTexture(GL_TEXTURE_2D, suelo);
	procesar_textura(suelo);

	//glColor3f(0, 0, 0);

	// Reinicio Z
	if (ini || (abs(abs(posZ) - abs(posAZ)) > 10 )) {
		posAZ = posZ;
		ini = false;
	}
	// Reinicio X
	/*
	 NO SALIO :( 
	if (ini || (abs(abs(posX) - abs(posAX)) > 10)) {
		if (alpha < 10)
			posAX = posAX - 15;
		else
			posAX = posAX + 15;
		ini = false;
	}*/

	GLfloat p0[3] = { 200 + posX, -0.01, -200 - posZ + posAZ},
		p1[3] = { 200 + posX, -0.01, 200 - posZ + posAZ },
		p2[3] = { -200 + posX, -0.01, 200 + posZ + posAZ },
		p3[3] = { -200 + posX, -0.01, -200 - posZ + posAZ };

	quadtex(p0, p1, p2, p3, 0, 40, 0, 40, 500, 500);
	glPopMatrix();
}

void drawCylinder(int numMajor, int numMinor, float height, float radius, int x, int z, float rotar = 90)
// Sirve para dibujar los postes de los banners
{
	double majorStep = height / numMajor;
	double minorStep = 2.0 * PI / numMinor;
	int i, j;

	for (i = 0; i < numMajor; ++i) {
		GLfloat z0 = 0.5 * height - i * majorStep;
		GLfloat z1 = z0 - majorStep;
		glPushMatrix();
		//glColor3f(1, 1, 1);
		glTranslatef(x, 0, z);
		glRotatef(rotar, 1, 0, 0);
		glBegin(GL_TRIANGLE_STRIP);
		for (j = 0; j <= numMinor; ++j) {
			double a = j * minorStep;
			GLfloat x = radius * cos(a);
			GLfloat y = radius * sin(a);
			glNormal3f(x / radius, y / radius, 0.0);
			glTexCoord2f(j / (GLfloat)numMinor, i / (GLfloat)numMajor);
			glVertex3f(x, y, z0);

			glNormal3f(x / radius, y / radius, 0.0);
			glTexCoord2f(j / (GLfloat)numMinor, (i + 1) / (GLfloat)numMajor);
			glVertex3f(x, y, z1);
		}
		glEnd();
		glPopMatrix();
	}
}

void generar_covid(float radius, float posx, float posz, int slices, int stacks) {
	GLUquadric* covid;
	covid = gluNewQuadric();

	glPushMatrix();
	//glEnable(GL_TEXTURE_2D);
	//glColor3f(0, 0, 0);
	glBindTexture(GL_TEXTURE_2D, covid_t);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 10);
	gluQuadricTexture(covid, 1);
	glTranslatef(posx, 0.5, posz);
	glRotatef(-90, 0, 1, 0);
	gluSphere(covid, radius, slices, stacks);
	glPopMatrix();
}

void permitir_contar1(int seg) {
	contar1 = true;
}

void permitir_contar2(int seg) {
	contar2 = true;
}

void permitir_contar3(int seg) {
	contar3 = true;
}

void permitir_contar4(int seg) {
	contar4 = true;
}

void generar_banners() {
	int aux = (int)posZ;
	int multiplo100 = aux / 100;
	int i1 = 100 * multiplo100 + 25;
	int i2 = 100 * multiplo100 + 50;
	int i3 = 100 * multiplo100 + 75;
	int i4 = 100 * multiplo100 + 90;


	glPushMatrix();
	// ======================================================================
	//Banner 1
	float tz = trazado(i1, A, T);
	GLfloat v1[3] = { tz - 3,2, i1 },
		v2[3] = { tz + 3,2, i1 },
		v3[3] = { tz + 3,5, i1 },
		v4[3] = { tz - 3,5, i1 };

	procesar_textura(banner1);
	glPolygonMode(GL_FRONT, modo_dibujo);
	//glColor3f(1, 0.5, 0.5);
	quad(v2, v1, v4, v3, 30, 30);

	// Farola 1
	GLfloat pos_farola1[] = { tz, ALTURA_FAROLA, i1, 1.0 };
	glLightfv(GL_LIGHT2, GL_POSITION, pos_farola1);

	// COVID 1
	//generar_covid(float radius, float posx, float posz, int slices, int stacks)
	procesar_textura(banner1);
	generar_covid(0.25, tz, i1, 50, 50);

	// Compruebo si el jugador paso al lado de la plasma para contarsela
	float distancia1 = sqrt((tz - posX) * (tz - posX) + (i1 - posZ) * (i2 - posZ));
	if (distancia1 <= DISTANCIA_PLASMA && contar1) {
		plasma++;
		contar1 = false;
		glutTimerFunc(1500, permitir_contar1, 1500);
	}

	// ======================================================================
	//Banner 2
	float tz2 = trazado(i2, A, T);
	GLfloat v5[3] = { tz2 - 3,2, i2 },
		v6[3] = { tz2 + 3,2, i2 },
		v7[3] = { tz2 + 3,5, i2 },
		v8[3] = { tz2 - 3,5, i2 };

	procesar_textura(banner2);
	glPolygonMode(GL_FRONT, modo_dibujo);
	//glColor3f(1, 0.5, 0.5);
	quad(v6, v5, v8, v7, 30, 30);

	// Farola 2
	GLfloat pos_farola2[] = { tz2, ALTURA_FAROLA, i2, 1.0 };
	glLightfv(GL_LIGHT3, GL_POSITION, pos_farola2);

	// COVID 2
	generar_covid(0.25, tz2, i2, 50, 50);

	// Compruebo si el jugador paso al lado de la plasma para contarsela
	float distancia2 = sqrt((tz2 - posX) * (tz2 - posX) + (i2 - posZ) * (i2 - posZ));
	if (distancia2 <= DISTANCIA_PLASMA && contar2) {
		plasma++;
		contar2 = false;
		glutTimerFunc(1500, permitir_contar2, 1500);
	}

	// ======================================================================
	//Banner 3
	float tz3 = trazado(i3, A, T);
	GLfloat v9[3] = { tz3 - 3,2, i3 },
		v10[3] = { tz3 + 3,2, i3 },
		v11[3] = { tz3 + 3,5, i3 },
		v12[3] = { tz3 - 3,5, i3 };

	procesar_textura(banner3);
	glPolygonMode(GL_FRONT, modo_dibujo);
	//glColor3f(1, 0.5, 0.5);
	quad(v10, v9, v12, v11, 30, 30);

	// Farola 3
	GLfloat pos_farola3[] = { tz3, ALTURA_FAROLA, i3, 1.0 };
	glLightfv(GL_LIGHT4, GL_POSITION, pos_farola3);

	// COVID 3
	generar_covid(0.25, tz3, i3, 50, 50);

	// Compruebo si el jugador paso al lado de la plasma para contarsela
	float distancia3 = sqrt((tz3 - posX) * (tz3 - posX) + (i3 - posZ) * (i3 - posZ));
	if (distancia3 <= DISTANCIA_PLASMA && contar3) {
		plasma++;
		contar3 = false;
		glutTimerFunc(1500, permitir_contar3, 1500);
	}
	// ======================================================================
	//Banner 4
	float tz4 = trazado(i4, A, T);
	GLfloat v13[3] = { tz4 - 3,2, i4 },
		v14[3] = { tz4 + 3,2, i4 },
		v15[3] = { tz4 + 3,5, i4 },
		v16[3] = { tz4 - 3,5, i4 };

	procesar_textura(banner4);
	glPolygonMode(GL_FRONT, modo_dibujo);
	//glColor3f(1, 0.5, 0.5);
	quad(v14, v13, v16, v15, 30, 30);
	glPopMatrix();

	// Farola 4
	GLfloat pos_farola4[] = { trazado(i4, A, T), ALTURA_FAROLA, i4, 1.0 };
	glLightfv(GL_LIGHT5, GL_POSITION, pos_farola4);

	// COVID 4
	procesar_textura(banner1);
	generar_covid(0.25, tz4, i4, 50, 50);

	// Compruebo si el jugador paso al lado de la plasma para contarsela
	float distancia4 = sqrt((tz4 - posX) * (tz4 - posX) + (i4 - posZ) * (i4 - posZ));
	if (distancia4 <= DISTANCIA_PLASMA && contar4) {
		plasma++;
		contar4 = false;
		glutTimerFunc(1500, permitir_contar4, 1500);
	}

	// Pilares
	procesar_textura(metal);

	drawCylinder(50, 50, 10, 0.25, v1[0], v1[2]);
	drawCylinder(50, 50, 10, 0.25, v2[0], v2[2]);

	drawCylinder(50, 50, 10, 0.25, v5[0], v5[2]);
	drawCylinder(50, 50, 10, 0.25, v6[0], v6[2]);

	drawCylinder(50, 50, 10, 0.25, v9[0], v9[2]);
	drawCylinder(50, 50, 10, 0.25, v10[0], v10[2]);

	drawCylinder(50, 50, 10, 0.25, v15[0], v15[2]);
	drawCylinder(50, 50, 10, 0.25, v16[0], v16[2]);
}



void generar_carretera(int longitud) {
	for (int i = -(posZ + 1); i < posZ + longitud; i++) {
		GLfloat v3[3] = { trazado(i + 1, A, T) + 2,0, i + 1 },
			v0[3] = { trazado(i + 1, A, T) - 2,0, i + 1 },
			v1[3] = { trazado(i, A, T) - 2,0,i },
			v2[3] = { trazado(i, A, T) + 2,0,i };
		glPushMatrix();
		procesar_textura(carretera);

		// Actualizo el material especifico para la carretera
		glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, carretera_dif);
		glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, carretera_esp);
		glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 3);

		glPolygonMode(GL_FRONT, modo_dibujo);
		//glColor3f(1, 0.5, 0.5);
		quad(v0, v3, v2, v1, 30, 30);
		//ejes();
		glPopMatrix();
	}
}

void coche() {
	// Capo
	glEnable(GL_TEXTURE_2D);
	glEnable(GL_LIGHTING);
	glPushMatrix();
	procesar_textura(capo);
	glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, ROJO);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, ROJO);
	glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 3);
	glBegin(GL_QUADS);
	glTexCoord2f(0, 1);
	glVertex3f(-0.5, -0.6, -2);
	glTexCoord2f(1, 1);
	glVertex3f(0.5, -0.6, -2	);
	glTexCoord2f(1, 0);
	glVertex3f(1, -1, -2);
	glTexCoord2f(0, 0);
	glVertex3f(-1, -1, -2);
	glEnd();
	glPopMatrix();

	// Volante
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	procesar_textura(metal);
	glBegin(GL_QUADS);
	for (float r = 0.2; r < 0.3; r += 0.001) {
		for (int ii = 0; ii < 50; ii++) {
			float theta = 2.0f * 3.1415926f * float(ii) / float(50);//get the current angle 
			float x = r * cosf(theta);//calculate the x component 
			float y = r * sinf(theta);//calculate the y component 
			glPushMatrix();
			glRotatef(-90, 0, 1, 0);
			glVertex3f(x + 0, y - 0.7, -1.5);//output vertex 
			glPopMatrix();
		}
	}
	glEnd();
	glPopMatrix();

	// Raya volante
	glPushMatrix();
	procesar_textura(suelo);
	glTranslatef(0, -0.69, 0);
	glRotatef(alpha_volante, 0, 0, 1);
	glCallList(rectangulo);
	glPopMatrix();
}

void generar_mundo() {
	generar_carretera(100);

	if (modo_fondo == PERSONALIZADO) {
		aplicar_textura_cielo(100);
		aplicar_textura_suelo(10, 22);
	}
	else {
		// Por si se necesita la parte de la practica
		GLUquadric* quadratic;
		quadratic = gluNewQuadric();

		glPushMatrix();
		glBindTexture(GL_TEXTURE_2D, desierto);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		gluQuadricTexture(quadratic, 1);

		glTranslatef(posX, -50, posZ);
		//glColor3f(0, 0, 1);
		glRotatef(-90, 1, 0, 0);
		gluCylinder(quadratic, 100, 100, 100, 50, 50);
		glPopMatrix();
	}

	iluminacion();
	generar_banners();
}

// ===========================================================================================
// Funciones predefinidas
// ===========================================================================================
void display() {
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	//glMatrixMode(GL_MODELVIEW);
	glNormal3f(0, 1, 0);
	glColor3f(0.5, 0.5, 0.5);

	// =========================================================================
	// Iluminacion
	// =========================================================================
	//Colocamos la luna
	GLfloat posicionLuna[] = { 0.0, 10.0, 0.0, 0.0 };
	glLightfv(GL_LIGHT0, GL_POSITION, posicionLuna);
	

	//glLoadIdentity();
	GLfloat pos_faro[] = { 0.0, 0.7, 0.0, 1.0 };
	glLightfv(GL_LIGHT1, GL_POSITION, pos_faro);
	
	if (MIRAR_ARRIBA) {
		GLfloat dir_faro[] = { 0.0, -0.1, DIR_FARO }; // A donde apunta el foco
		glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, dir_faro);
	}
	else {
		GLfloat dir_faro[] = { 0.0, -0.5, DIR_FARO }; // A donde apunta el foco
		glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, dir_faro);
	}
	glPopMatrix();

	// =========================================================================
	// Extras
	// =========================================================================
	if(modo_hud == ON){
		// Dibuja elementos solidarios a la camara
		glPushAttrib(GL_ALL_ATTRIB_BITS);
		dibujar_barra_velocidad();
		coche();
		glPopAttrib();
	}
	// =========================================================================
	// Vista
	// =========================================================================
	gluLookAt(posX, 1, posZ, posX + despX, 1, posZ + despZ, 0, 1, 0);
	//gluLookAt(posX, 5, posZ, posX, 1, posZ + despZ, 0, 1, 0); // Para ver los ejes
	//gluLookAt(posX, 200, posZ, posX, 1, posZ + despZ, 0, 1, 0); // Para ver el carril desde muuuy arriba y bien

	// =========================================================================
	// Modos
	// =========================================================================
	dibujar_modo_dibujo();
	dibujar_modo_dia();
	dibujar_modo_niebla();

	// Mundo
	generar_mundo();

	if (modo_dia == DIURNO)
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
	else
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

	glutSwapBuffers();
}

void reshape(GLint w, GLint h)
// Funcion de atencion al redimensionamiento
{
	glViewport(0, 0, w, h);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	float razon = (float)w / h;
	gluPerspective(45, razon, 1, 100);// VISION
}

void actualizar_camera() {
	static int ini = glutGet(GLUT_ELAPSED_TIME);
	int ahora = glutGet(GLUT_ELAPSED_TIME);
	float dif = (ahora - ini) / 1000.0f;

	// Ticks transcurridos desde el principio
	ticks += tick * 360 * dif;

	// Movimiento de la camera
	desplazamiento = dif * velocidad;
	ini = ahora;

	posX += desplazamiento * despX;
	posZ += desplazamiento * despZ;

	glutPostRedisplay();
}

void onTimer(int t)
// Actualiacion periodica de la camera
{
	stringstream titulo;
	titulo << "Proyecto conducción. Velocidad: " << velocidad << "m/s: Plasmas: " << plasma;
	glutSetWindowTitle(titulo.str().c_str());

	glutTimerFunc(t, onTimer, t);
	actualizar_camera();
}


void onSpecialKey(int tecla, int x, int y)
// Listener de las flechas de movimiento
{
	switch (tecla) {
	case GLUT_KEY_UP: {

		if (TOPE_VELOCIDAD == -1 || velocidad < TOPE_VELOCIDAD)
			velocidad += INCREMENTO_VELOCIDAD;
		break;
	}
	case GLUT_KEY_DOWN: {
		if (velocidad >= 0.01)
			velocidad -= INCREMENTO_VELOCIDAD;
		else
			velocidad = 0; // A veces se buguea y salen numero redondeados negativos muy pequeños, esto lo evita
		break;
	}
	case GLUT_KEY_LEFT: {
		if (alpha <= TOPE_ALPHA)
			alpha += INCREMENTO_ALPHA;
		alpha_volante = alpha;
		break;
	}
	case GLUT_KEY_RIGHT: {
		if (alpha >= -TOPE_ALPHA)
			alpha -= INCREMENTO_ALPHA;
		alpha_volante = alpha;
		break;
	}
	}

	// Actualizacion de los angulos
	despX = sin(alpha * PI / 180);
	despZ = cos(alpha * PI / 180);

	glutPostRedisplay();
}

void onKey(unsigned char tecla, int x, int y)
// Funcion de atencion al teclado
{
	switch (tecla) {
	case 'S': {
		// Activa el modo alambrico
		modo_dibujo = ALAMBRICO;
		glDisable(GL_LIGHTING);
		glDisable(GL_TEXTURE_2D);
		break;
	}
	case 's': {
		// Desactiva el modo alambrico
		modo_dibujo = SOLIDO;
		glEnable(GL_LIGHTING);
		glEnable(GL_TEXTURE_2D);
		break;
	}
	case 'L': {
		modo_dia = DIURNO;
		break;
	}
	case 'l': {
		modo_dia = NOCTURNO;
		break;
	}
	case 'N': {
		modo_niebla = ON;
		break;
	}
	case 'n': {
		modo_niebla = OFF;
		break;
	}
	case 'C': {
		modo_hud = ON;
		break;
	}
	case 'c': {
		modo_hud = OFF;
		break;
	}
	case 'p': {
		cout << "Guardando captura..." << endl;
		saveScreenshot((char*)"../imgs/juego.jpg", 600, 600);
		break;
	}
	case 'z': {
		if (LUCES_LARGAS)
			LUCES_LARGAS = false;
		else
			LUCES_LARGAS = true;
		break;
	}
	case 'a': {
		if (MIRAR_ARRIBA)
			MIRAR_ARRIBA = false;
		else
			MIRAR_ARRIBA = true;
		break;
	}

	case 27: // Pulso escape
		exit(0);
	}
	glutPostRedisplay();
}


void main(int argc, char** argv)
// Programa principal
{
	glutInit(&argc, argv); // Inicializacion de GLUT
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH); // Alta de buffers a usar
	glutInitWindowSize(900, 600); // Tamanyo inicial de la ventana
	glutCreateWindow(TITULO_VENTANA); // Creacion de la ventana con su titulo
	std::cout << TITULO_VENTANA << " running" << std::endl; // Mensaje por consola
	glutDisplayFunc(display); // Alta de la funcion de atencion a display
	glutReshapeFunc(reshape); // Alta de la funcion de atencion a reshape
	glutKeyboardFunc(onKey); // Alta de la funcion de atencion al teclado
	glutTimerFunc(1000 / FPS, onTimer, 1000 / FPS); // Refrescar la pantalla
	glutSpecialFunc(onSpecialKey); // Listener de flechas
	glEnable(GL_LIGHTING); // Habilita las luces
	init(); // Inicializacion propia
	glutMainLoop(); // Puesta en marcha del programa
}