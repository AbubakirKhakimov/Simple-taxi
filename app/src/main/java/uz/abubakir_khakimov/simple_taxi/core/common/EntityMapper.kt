package uz.abubakir_khakimov.simple_taxi.core.common

interface EntityMapper<FirstEntity, SecondEntity> {

    fun mapTo(entity: FirstEntity): SecondEntity

    fun mapToList(entityList: List<FirstEntity>): List<SecondEntity>{
        throw NotImplementedException(NOT_IMPLEMENTED_ERROR_MASSAGE)
    }

    fun <K> mapToMapValue(entityMap: Map<K, FirstEntity>): Map<K, SecondEntity>{
        throw NotImplementedException(NOT_IMPLEMENTED_ERROR_MASSAGE)
    }

    fun <K> mapToMapValueList(entityMapList: Map<K, List<FirstEntity>>): Map<K, List<SecondEntity>>{
        throw NotImplementedException(NOT_IMPLEMENTED_ERROR_MASSAGE)
    }

    fun <V> mapToMapKey(entityMap: Map<FirstEntity, V>): Map<SecondEntity, V>{
        throw NotImplementedException(NOT_IMPLEMENTED_ERROR_MASSAGE)
    }

    fun <V> mapToMapKeyList(entityMapList: Map<List<FirstEntity>, V>): Map<List<SecondEntity>, V>{
        throw NotImplementedException(NOT_IMPLEMENTED_ERROR_MASSAGE)
    }

    fun mapToResult(entityResult: Result<FirstEntity>): Result<SecondEntity> {
        throw NotImplementedException(NOT_IMPLEMENTED_ERROR_MASSAGE)
    }

    fun mapToResultList(entityResultList: Result<List<FirstEntity>>): Result<List<SecondEntity>> {
        throw NotImplementedException(NOT_IMPLEMENTED_ERROR_MASSAGE)
    }

    companion object{

        const val NOT_IMPLEMENTED_ERROR_MASSAGE = "This function is not implemented!" +
                " First implement and write the body!"
    }
}