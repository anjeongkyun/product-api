package org.product.domainmodel.valueobject

@JvmInline
value class Money(
    val amount: Int,
) {
    init {
        require(amount >= 0) { "금액은 0보다 작을 수 없습니다." }
    }
}
