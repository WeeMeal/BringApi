import QrCode.Companion.parseToBringListLite
import domain.Product

class BringQrConverter(
    private val qrCode: QrCode,
    private val bringApi: BringApi
) {

    companion object {
        suspend fun BringQrConverter.addProductsToList() {
            qrCode.parseToBringListLite().purchase.forEach { product ->
                println(product)
                bringApi.addProductToList(
                    Product(
                        name = product.name, specification = product.specification
                    )
                )
            }
        }
    }


}