//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.7 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2016.09.20 à 11:09:14 AM CEST 
//


package ml.payme.operation.simpletransfert;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="team" type="{http://www.payme.ml/operation/simpleTransfert}team"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "team"
})
@XmlRootElement(name = "getSimpleTransfertRespnse")
public class GetSimpleTransfertRespnse {

    @XmlElement(required = true)
    protected Team team;

    /**
     * Obtient la valeur de la propriété team.
     * 
     * @return
     *     possible object is
     *     {@link Team }
     *     
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Définit la valeur de la propriété team.
     * 
     * @param value
     *     allowed object is
     *     {@link Team }
     *     
     */
    public void setTeam(Team value) {
        this.team = value;
    }

}
