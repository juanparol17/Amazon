package example;
/**
 * Clase AppTest.java
 * Fecha: 19-01-2021
 * @author Juan Pablo Sacnhez
 * @since 1.1
 */

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * la clase AppTest permite ejecutar 4 test al sitio www.Amazon.com.
 * Esta usa maven, selenium y testng para la ejecucion de los test automatizados
 * Se adiciono testng xml para los reportes de los test (Se puede revisar el archivo emailable-report.html).
 * A modo general esta clase a tra vez de sus metodos, permitira el acceso al sitio web indicado, buscara un libro, lo seleccionara, validara
 * su titulo la preseleccion de un elemento web, validara la logica de los precios del libro y finalmente validara que la geolocalizacion del
 * sitio amazon sea correcto.
 *
 * Se define la version 1.0 con la clase inicial enviada para hacer las modificaciones por lo que esta será la version 1.1 con las modificaciones
 * ya hechas
 *
 * @author Juan Pablo Sacnhez
 * @since 1.1
 * @see
 */

public class AppTest {

    /**
     * A dieferencia de la version 1.0 en esta clase se definen las variables
     * Variables usadas para los test automaticos
     * @author Juan Pablo Sacnhez
     * @since 1.1
     */
    private WebDriver driver;
    private String baseUrl;
    private String selectedcolorpaperBack="rgba(199, 81, 31, 1)";
    private URL locationPage;
    private BufferedReader entrada;
    private String codigo,c;
    private String pais, paisAmazon;
    private double listPriced, newBuyBoxPriced,savingsAmountd, usedPriced;
    private boolean menor=true;

    /**
     * A diferencia de la version 1.0 en esta clase se definen los localizadores previos a su uso en los metodos
     * Localizadores usados en los test automaticos
     * @author Juan Pablo Sacnhez
     * @since 1.1
     */

    By miprimerLibro=By.linkText("A Practitioner's Guide to Software Test Design");
    By titulomiprimerlibro=By.id("productTitle");
    By paperBack=By.cssSelector("#mediaTab_heading_2 .a-size-base");
    By deliveryTo=By.cssSelector(".a-declarative:nth-child(2) #contextualIngressPtLabel span:nth-child(2)");
    By listPrice= By.id("listPrice");
    By newBuyBoxPrice=By.id("newBuyBoxPrice");
    By savingsAmount= By.id("savingsAmount");
    By usedPrice=By.id("usedPrice");


    /**
     * En esta version se usan las anotaciones de testng beforetest, test "en esta priorizando los test prerequisitos a otros test" y aftertest
     *
     * Anotacion @BeforeTest metodo seTup
     * Se define el navegador
     * Se crea el objeto para inicializar el navegador
     *
     * @author Juan Pablo Sacnhez
     * @since 1.1
     */
    @BeforeTest
    public void seTup(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver =new ChromeDriver();

    }

    /**
     * Anotacion @Test metodo searchBook con priotity 0
     * Metodo que permite buscar un libro, ingresar a sus detalles, verificar su existencia y titulo
     *
     * se accede al sitio www.amazon.com
     * se define un tiempo implicito de espera de 3 segundos
     * Se localiza la barra de busqueda en amazon por medio del id
     * Se realiza la busqueda de un libro
     *
     * La estrategia para localizar el libro se modifica, en esta version se realiza por medio de linkText, debido a que no se encontro por medio
     * de la estrategia de localizacion indicada inicialmente
     *
     * El primer Assert verifica que el libro con el texto buscado se encuentra en la pagina
     * Doy click al enlace del libro mencionado
     *
     * La estrategia para localizar el titulo del libro se modifica de cssSelector para buscarlo por id
     *
     * El segundo Assert verifica que el titulo corresponda con el libro seleccionado
     *
     * @author Juan Pablo Sacnhez
     * @since 1.1
     *
     */
    @Test (priority = 0)
    public void searchBook(){
        baseUrl="https://www.amazon.com/";
        driver.get(baseUrl);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("input[id='twotabsearchtextbox']")).sendKeys("Software Test Design");
        driver.findElement(By.cssSelector("input[id='twotabsearchtextbox']")).sendKeys(Keys.ENTER);
        Assert.assertTrue(driver.findElement(miprimerLibro).isDisplayed());
        driver.findElement(miprimerLibro).click();
        Assert.assertEquals(driver.findElement(titulomiprimerlibro).getText(), "A Practitioner's Guide to Software Test Design");



    }

    /**
     * Anotacion @Test metodo verifyPreloads con priotity 1
     * Metodo que permite verificar si el paperbak es mostrado y si esta preseleccionado
     *
     *
     * Para esta version se modifica el objeto a localizar  debido a que al ingresar a la pagina inicialmente aparece preseleccionado el libro de
     * pastablanda por lo que se cambia el localizador para el objeto preseleccionado y verifico si esta preseleccionado por medio del color de la
     * letra
     *
     * Se establece 3 segundos de espera implicita
     * El primer Assert verifica si el paperback es mostrado
     * una vez obtengo el color que se usa cuando un tipo de pasta esta seleccionado, lo defino en la constante selectedcolorpaperBack
     * Obtengo el color del paperback
     * El segundo Assert compara el color obtenido con el color de la constante selectedcolorpaperBack
     *
     * @author Juan Pablo Sacnhez
     * @since 1.1
     * @throws Exception
     */
    @Test (priority = 1)
    public void  verifyPreloads(){
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        Assert.assertTrue(driver.findElement(paperBack).isDisplayed());
        Color colorletra = Color.fromString(driver.findElement(paperBack).getCssValue("Color"));
        assert colorletra.asRgba().equals(selectedcolorpaperBack);
        System.out.println("\n\nLa pasta blanda se muestra preseleccionada  ");

    }

    /**
     * Anotacion @Test metodo verifyLocation con priotity 2
     * Metodo que permite verificar cuando amazon muestra la opcion de enviar al pais de donde es consultado en este caso "Colombia", si realmente
     * el pais de donde es consultado el/los productos es el pais mencionado por amazon. Esto se comprobara comparando el dato con un sitio que
     * por medio de la ip publica nos dice desde que pais se esta accediendo a la URL.
     *
     * Se crea un objeto URl para el sitio https://es.geoipview.com/
     * Se almacena la fuente de la pagina en el BufferReader entrada
     * Se leen toda la cadena almacenadas en el Buffer
     * Se ejecuta el metodo buscarPais()
     *
     * Se obtiene el pais a enviar en la pagina de Amazon por medio de css en
     * La estrategia de localizacion para obtener el pais a donde Amazon enviara el pedido es por medio de cssSelector el localizador es deliveryTo
     *
     * El unico Assert del metodo compara el pais entregado por el metodo buscarPais con Pais entregado por Amazon
     * @author Juan Pablo Sacnhez
     * @since 1.1
     *
     * @throws IOException
     * @throws MalformedURLException
     */
    @Test (priority = 2)
    public void verifyLocation() throws IOException,MalformedURLException {
        locationPage=new URL("https://es.geoipview.com/");
        entrada=new BufferedReader(new InputStreamReader(locationPage.openStream()));
        c=entrada.readLine();
        while(c!=null){
            codigo=codigo+c;
            c=entrada.readLine();
            pais=buscarPais();
        }

        paisAmazon=driver.findElement(deliveryTo).getText();
        Assert.assertEquals(pais,paisAmazon);

        System.out.println( "\n\nVerificacion de pais a enviar:  Pais Geoipview: "+ pais+" vs Pais Amazon: "+paisAmazon);
    }

    /**
     * Metodo buscarPais
     * Este metodo permitira buscar la cadena entregada por el sitio https://es.geoipview.com/ que corresponde al pais desde donde se accede a la
     * URL
     *
     * se definen variables enteras para recorrer la cadena inicio y fin
     * se usan for e if para recorrer los datos almacenados en el String codigo de forma que se encuentre con parte de la etiqueta "absmiddle"
     * Se unica la posicion de la cadena en "inicio" con 15 caracteres adicionales
     * Se ubica la posicion de la cadena en "fin" cuando termina con el caracter "
     * Se retorna un substring de la cade en la posicion inicio  y fin para obtener la cadena del pais entregada por el sitio externo a Amazon
     * @author Juan Pablo Sacnhez
     * @since 1.1
     *
     * @return etiqueta del pais entregado por sitio https://es.geoipview.com/
     */
    private String buscarPais() {
        int inicio=0;
        int fin=0;
        for(int i=0;i<codigo.length();i++)
            if(codigo.charAt(i)=='a'){
                if(codigo.charAt(i+1)=='b'){
                    if(codigo.charAt(i+2)=='s'){
                        if(codigo.charAt(i+3)=='m'){
                            if(codigo.charAt(i+4)=='i'){
                                if(codigo.charAt(i+5)=='d'){
                                    if(codigo.charAt(i+6)=='d'){
                                        if(codigo.charAt(i+7)=='l'){
                                            inicio=i+15;
                                            int x=inicio;
                                            while(codigo.charAt(x)!='"'){
                                                x++;
                                            }
                                            fin=x;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        return codigo.substring(inicio,fin);
    }

    /**
     * Anotacion @Test metodo verifyLocation con priotity 3
     * Metodo que permite verificar si el precio del libro usado, libro nuevo precio total, precio con descuento son menores o iguales a cero
     * tambien verifica si el descuento es menor a cero y finalmente verifica si el valor del precio final es igual a preciototal menos el
     * descuento
     *
     * La estrategia de localizacion para obtener listPrice, newBuyBoxPrice, savingsAmount, usedPrice es por id
     * Se asignan estos valores a variables tipo duoble reemplazando el signo "$" por nada
     * Se debe tener en cuenta que el sistema oeprativo del equipo tiene como separador de decimales el punto '.'
     *
     * El primer Assert verifica si el precio con descuento es igual al precio total menos el decuento.
     * El segundo Assert verifica si el metodo menoroigualacero devuelve true o false.
     *
     * @author Juan Pablo Sacnhez
     * @since 1.1
     *
      */
    @Test (priority = 3)
    public void verificarPrecio(){

    listPriced =Double.parseDouble(driver.findElement(listPrice).getText().replace("$",""));
    newBuyBoxPriced =Double.parseDouble(driver.findElement(newBuyBoxPrice).getText().replace("$",""));
    savingsAmountd =Double.parseDouble(driver.findElement(savingsAmount).getText().replace("$",""));
    usedPriced =Double.parseDouble(driver.findElement(usedPrice).getText().replace("$",""));

        Assert.assertEquals(newBuyBoxPriced,(listPriced-savingsAmountd));
        Assert.assertTrue(menoroigualcero());

        System.out.println("\nVerificaciones de precios: \n\n Precio recomendado: "+listPriced+"\n Precio definitivo: "+newBuyBoxPriced+"\n Ahorra: "+savingsAmountd+"\n Precio usado:  "+usedPriced);

    }

    /**
     * Metodo menor o igual a cero
     *
     * Este metodo permite evaluar si el precio con descuento o el  precio total o el precio del libro usado es menor o igual a cero, tambien
     * permite verificar si el valor del descuento es menor a cero
     *
     * Se usa if para determinar valores menores o iguales a cero en el caso de los precios y para el descuento solo menor a cero
     *
     * la variable menor que inicialmente esta definida como true cambiara a false en caso de que algunas de estas condiciones se cumplan
     * y  fallara el assertion
     *
     * @author Juan Pablo Sacnhez
     * @since 1.1
     *
     *
     * @return boolean false precios menores o iguales a cero / true si los precios son mayores a cero. (para el descuento se valida solo si
     * es menor a cero)
     */
    public boolean menoroigualcero() {
        if ((listPriced<=0)||(newBuyBoxPriced<=0)||(usedPriced<=0)||(savingsAmountd<0)){

            menor=false;
        }
        return menor;
    }

    /**
     * Anotacion @AfterTest metodo tearDown
     * Metodo que permite cerrar el navegador despues de la ejecucion de los  test cases
     *
     * @author Juan Pablo Sacnhez
     * @since 1.1
     */

    @AfterTest
    public void tearDown(){
        driver.quit();
    }

}