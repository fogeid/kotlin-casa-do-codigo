package br.com.zup.validators

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CONSTRUCTOR
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

@MustBeDocumented
@Target(FIELD, CONSTRUCTOR)
@Retention(RUNTIME)
@Constraint(validatedBy = [ValorUnicoValidator::class])
annotation class ValorUnico(
    val message: String = "E-mail já cadastrado, insira um outro e-mail.",
    val fieldName: String,
    val obj: KClass<*>,
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
) {

}

@Singleton
open class ValorUnicoValidator(private var manager: EntityManager): ConstraintValidator<ValorUnico, Any> {
    private lateinit var fieldId: String
    private lateinit var obj: KClass<*>

    override fun initialize(annotation: ValorUnico?) {
        this.obj = annotation!!.obj
        this.fieldId = annotation.fieldName
    }

    @Transactional
    override fun isValid(
        value: Any?,
        annotationMetadata: AnnotationValue<ValorUnico>,
        context: ConstraintValidatorContext
    ): Boolean {
        return manager.createQuery("SELECT 1 FROM ${obj.qualifiedName} WHERE  $fieldId = :field")
            .setParameter("field", value)
            .resultList
            .isEmpty();
    }
}