//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.07.31 at 05:13:38 PM IDT 
//


package LogicEngine.generatedClasses;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the resoures.UI.generats package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Type_QNAME = new QName("", "Type");
    private final static QName _Name_QNAME = new QName("", "Name");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: resoures.UI.generats
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Player }
     * 
     */
    public Player createPlayer() {
        return new Player();
    }

    /**
     * Create an instance of {@link Game }
     * 
     */
    public Game createGame() {
        return new Game();
    }

    /**
     * Create an instance of {@link Board }
     * 
     */
    public Board createBoard() {
        return new Board();
    }

    /**
     * Create an instance of {@link GameDescriptor }
     * 
     */
    public GameDescriptor createGameDescriptor() {
        return new GameDescriptor();
    }

    /**
     * Create an instance of {@link Players }
     * 
     */
    public Players createPlayers() {
        return new Players();
    }

    /**
     * Create an instance of {@link DynamicPlayers }
     * 
     */
    public DynamicPlayers createDynamicPlayers() {
        return new DynamicPlayers();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Type")
    public JAXBElement<String> createType(String value) {
        return new JAXBElement<String>(_Type_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

}
