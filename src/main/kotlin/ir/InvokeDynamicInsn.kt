package ir

class InvokeDynamicInsn(op: Int, val lambdaOwner: String, val lambdaName: String, val lambdaDescription: String, val name: String, val type: String, val isInterface: Boolean) : Insn(op) {

    override fun toString(): String = "lambda_owner: $lambdaOwner" +
            "lambda_name: $lambdaName" +
            "lambda_desc $lambdaDescription" +
            "name $name" +
            "type $type"
}
