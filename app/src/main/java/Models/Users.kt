package Models

class Users {
    var id: Int? = null
    var name: String? = null
    var sureName: String? = null
    var fatherName: String? = null
    var countryName: String? = null
    var cityName: String? = null
    var addressHouse: String? = null
    var dateReceipt: String? = null
    var dateTerm: String? = null
    var M_or_F: String? = null


    constructor(
        name: String?,
        sureName: String?,
        fatherName: String?,
        countryName: String?,
        cityName: String?,
        addressHouse: String?,
        dateReceipt: String?,
        dateTerm: String?,
        M_or_F: String?
    ) {
        this.name = name
        this.sureName = sureName
        this.fatherName = fatherName
        this.countryName = countryName
        this.cityName = cityName
        this.addressHouse = addressHouse
        this.dateReceipt = dateReceipt
        this.dateTerm = dateTerm
        this.M_or_F = M_or_F
    }


    constructor(
        id: Int?,
        name: String?,
        sureName: String?,
        fatherName: String?,
        countryName: String?,
        cityName: String?,
        addressHouse: String?,
        dateReceipt: String?,
        dateTerm: String?,
        M_or_F: String?
    ) {
        this.id = id
        this.name = name
        this.sureName = sureName
        this.fatherName = fatherName
        this.countryName = countryName
        this.cityName = cityName
        this.addressHouse = addressHouse
        this.dateReceipt = dateReceipt
        this.dateTerm = dateTerm
        this.M_or_F = M_or_F
    }

    constructor()
}