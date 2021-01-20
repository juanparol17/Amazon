# AmazonTestAutomation

## Introduction

Este proyecto consiste en automatizar TestCases, para este caso particular se automatizaran 4 test para el sitio www.Amazon.com, donde se realizaran búsquedas, validaciones de objetos preseleccionados y validaciones de consistencia de la información entregada por sitio, incluso en algún caso se validara versus información de un sitio de terceros.

## Code Samples

Como codigo de ejemplo se puede tener el metodo para verificar el precio proporcionado>

public void verificarPrecio(){

    listPriced =Double.parseDouble(driver.findElement(listPrice).getText().replace("$",""));
    newBuyBoxPriced =Double.parseDouble(driver.findElement(newBuyBoxPrice).getText().replace("$",""));
    savingsAmountd =Double.parseDouble(driver.findElement(savingsAmount).getText().replace("$",""));
    usedPriced =Double.parseDouble(driver.findElement(usedPrice).getText().replace("$",""));

        Assert.assertEquals(newBuyBoxPriced,(listPriced-savingsAmountd));
        Assert.assertTrue(menoroigualcero());

        System.out.println("\nVerificaciones de precios: \n\n Precio recomendado: "+listPriced+"\n Precio definitivo: "+newBuyBoxPriced+"\n Ahorra: "+savingsAmountd+"\n Precio usado:  "+usedPriced);

    }

## Installation

Este es un proyecto que usa Java/Selenium/TestNg/Maven

La clase AppTest.java se encuentra en la ruta: src/main/java/example/


- Este proyecto se desarrollo con la version de intelij idea:

IntelliJ IDEA 2020.3.1 (Community Edition)
Build #IC-203.6682.168, built on December 29, 2020
Runtime version: 11.0.9.1+11-b1145.63 amd64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
Windows 10 10.0
GC: ParNew, ConcurrentMarkSweep
Memory: 1949M
Cores: 8
Non-Bundled Plugins: com.alibaba.autonavi.qa.testng, org.jetbrains.kotlin

- La version del jdk:

java version "1.8.0_271"
Java(TM) SE Runtime Environment (build 1.8.0_271-b09)
Java HotSpot(TM) 64-Bit Server VM (build 25.271-b09, mixed mode)

- Version de Maven:

Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: C:\Maven\apache-maven-3.6.3\bin\..
Java version: 1.8.0_261, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk1.8.0_261\jre
Default locale: en_US, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"

- Las dependencias Maven:

<dependencies>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>3.141.59</version>
        </dependency>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>7.3.0</version>
        </dependency>


- La version de ChromeDriver:

Version 87.0.4280.141 (Official Build) (64-bit)

- Se adiciono testng.xml para los reportes de ejecucion de los testCases

