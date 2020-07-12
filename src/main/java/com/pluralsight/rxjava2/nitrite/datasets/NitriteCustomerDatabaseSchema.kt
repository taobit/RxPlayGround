package com.pluralsight.rxjava2.nitrite.datasets

import com.pluralsight.rxjava2.nitrite.NitriteSchema
import com.pluralsight.rxjava2.nitrite.entity.Customer
import com.pluralsight.rxjava2.nitrite.entity.CustomerAddress
import com.pluralsight.rxjava2.nitrite.entity.CustomerProductPurchaseHistory
import com.pluralsight.rxjava2.nitrite.entity.Product
import org.dizitart.no2.Nitrite
import java.util.*

class NitriteCustomerDatabaseSchema : NitriteSchema {
    private var Product1UUID: UUID? = null
    private var Product2UUID: UUID? = null
    private var Product3UUID: UUID? = null
    private var CustomerAddress1UUID: UUID? = null
    var CustomerAddress2UUID: UUID? = null
    var CustomerAddress3UUID: UUID? = null
    var Customer1UUID: UUID? = null
    var Customer2UUID: UUID? = null

    override fun applySchema(db: Nitrite) {

        // Create the customer data
        createCustomerData(db)

        // Create the Customer Address information
        createCustomerAddressData(db)

        // Create the product data
        createProductData(db)

        // Create purchase history data
        createPurchaseHistoryData(db)
    }

    private fun createProductData(db: Nitrite) {
        val productRepo = db.getRepository(Product::class.java)
        if (productRepo.find().totalCount() == 0) {

            // Create 3 products
            val product1 = Product(UUID.randomUUID(), "Galoshes of Mud Walking", "09498772650")
            val product2 = Product(UUID.randomUUID(), "Stick of Back Scratching +2", "32982349872")
            val product3 = Product(UUID.randomUUID(), "Soap of Cleansing", "48243939874")
            Product1UUID = product1.productId
            Product2UUID = product2.productId
            Product3UUID = product3.productId
            productRepo.insert(product1, product2, product3)
        }
    }

    private fun createCustomerAddressData(db: Nitrite) {
        // Make sure the CustomerAddress collection is created.
        val addressRepo = db.getRepository(CustomerAddress::class.java)

        // If there's no data, then add some.
        if (addressRepo.find().totalCount() == 0) {

            // Create 3 addresses for assignment to our 2 customers
            val address1 = CustomerAddress(
                    UUID.randomUUID(), Customer1UUID,
                    "4448 Wumpus Way", "Suite 404", "Bedford", "TN", "37554")
            val address2 = CustomerAddress(
                    UUID.randomUUID(), Customer1UUID,
                    "19564 Hunter Street", "", "Walabash", "NC", "27344")
            val address3 = CustomerAddress(
                    UUID.randomUUID(), Customer2UUID,
                    "332 Dunder-Muffler Road", "Ste 554", "Papertown", "PA", "17845")
            addressRepo.insert(address1, address2, address3)
            CustomerAddress1UUID = address1.customerAddressId
            CustomerAddress2UUID = address2.customerAddressId
            CustomerAddress3UUID = address3.customerAddressId
        }
    }

    private fun createCustomerData(db: Nitrite) {

        // Make sure the Customer collection is created.
        val customerRepo = db.getRepository(Customer::class.java)
        if (customerRepo.find().totalCount() == 0) {

            // Create 2 customers
            val customer1 = Customer(UUID.randomUUID(), "Donald", "Vanner")
            val customer2 = Customer(UUID.randomUUID(), "Lawrence", "Spacestrider")
            customerRepo.insert(customer1, customer2)
            Customer1UUID = customer1.customerId
            Customer2UUID = customer2.customerId
        }
    }

    private fun createPurchaseHistoryData(db: Nitrite) {
        val purchaseRepo = db.getRepository(CustomerProductPurchaseHistory::class.java)
        if (purchaseRepo.find().totalCount() == 0) {

            // Create purchase records for customer 1
            val purchase1 = CustomerProductPurchaseHistory(
                    UUID.randomUUID(), Customer1UUID, Product1UUID
            )
            val purchase2 = CustomerProductPurchaseHistory(
                    UUID.randomUUID(), Customer2UUID, Product2UUID
            )
            val purchase3 = CustomerProductPurchaseHistory(
                    UUID.randomUUID(), Customer2UUID, Product3UUID
            )
            purchaseRepo.insert(purchase1, purchase2, purchase3)
        }
    }
}