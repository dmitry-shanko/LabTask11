
package com.epam.taskeleven.server.soap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.epam.com/taskeleven/server/soap}newsType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="action" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="FINDALL"/>
 *             &lt;enumeration value="FINDBYID"/>
 *             &lt;enumeration value="DELETE"/>
 *             &lt;enumeration value="UPDATE"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "newsType"
})
@XmlRootElement(name = "ServiceRequest")
public class ServiceRequest {

    @XmlElement(type = Integer.class)
    protected List<Integer> id;
    protected NewsType newsType;
    @XmlAttribute(name = "action", required = true)
    protected String action;

    /**
     * Gets the value of the id property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the id property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getId() {
        if (id == null) {
            id = new ArrayList<Integer>();
        }
        return this.id;
    }

    /**
     * Gets the value of the newsType property.
     * 
     * @return
     *     possible object is
     *     {@link NewsType }
     *     
     */
    public NewsType getNewsType() {
        return newsType;
    }

    /**
     * Sets the value of the newsType property.
     * 
     * @param value
     *     allowed object is
     *     {@link NewsType }
     *     
     */
    public void setNewsType(NewsType value) {
        this.newsType = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAction(String value) {
        this.action = value;
    }

}
