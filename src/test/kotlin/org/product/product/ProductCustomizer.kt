package healingpaper.unittest.customizer

import autoparams.customization.Customizer
import autoparams.generator.ObjectContainer
import autoparams.generator.ObjectGenerator
import org.product.domainmodel.entities.Brand
import org.product.domainmodel.entities.Product
import org.product.domainmodel.entities.ProductCategory
import org.product.domainmodel.valueobject.Money
import java.util.*

class ProductCustomizer : Customizer {
    override fun customize(generator: ObjectGenerator): ObjectGenerator =
        ObjectGenerator { query, context ->
            if (query.type == Product::class.java) {
                ObjectContainer(
                    Product.create(
                        title = context.generate(String::class.java),
                        category = context.generate(ProductCategory::class.java),
                        brand = context.generate(Brand::class.java),
                        amount =
                            Money(
                                amount = Random().nextLong(1, 1000),
                            ),
                    ),
                )
            } else {
                generator.generate(query, context)
            }
        }
}
