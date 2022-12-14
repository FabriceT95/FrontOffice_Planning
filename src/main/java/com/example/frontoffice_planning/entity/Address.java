package Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Address")
public class Address {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_address")
    private Long idAddress;
    @Basic
    @Column(name = "city")
    private String city;

    @Basic
    @Column(name = "postal_code")
    private String postalCode;

   /* @OneToMany(targetEntity = Users.class)
    List<Users> usersList = new ArrayList<>();*/

    public Address(String city, String postalCode) {
        this.city = city;
        this.postalCode = postalCode;
    }

    public Address() {

    }

    public Address(Long idAddress, String city, String postalCode) {
        this.idAddress = idAddress;
        this.city = city;
        this.postalCode = postalCode;
    }

    public Long getIdAddress() {
        return idAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (idAddress != address.idAddress) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (postalCode != null ? !postalCode.equals(address.postalCode) : address.postalCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAddress.intValue();
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "idAddress=" + idAddress +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
