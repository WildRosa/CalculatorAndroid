package com.example.myapplication

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*

//import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Zero_btn.setOnClickListener {setTextFields("0")}
        One_btn.setOnClickListener { setTextFields("1") }
        Two_btn.setOnClickListener { setTextFields("2") }
        Three_btn.setOnClickListener { setTextFields("3") }
        Four_btn.setOnClickListener { setTextFields("4") }
        Five_btn.setOnClickListener { setTextFields("5") }
        Six_btn.setOnClickListener { setTextFields("6") }
        Seven_btn.setOnClickListener { setTextFields("7") }
        Eight_btn.setOnClickListener { setTextFields("8") }
        Nine_btn.setOnClickListener { setTextFields("9") }
        LBracket_btn.setOnClickListener { setTextFields("(") }
        RBracket_btn.setOnClickListener { setTextFields(")") }
        Div_btn.setOnClickListener { setTextFields("/") }
        Mul_btn.setOnClickListener { setTextFields("*") }
        Sub_btn.setOnClickListener { setTextFields("-") }
        Add_btn.setOnClickListener { setTextFields("+") }
        Dot_btn.setOnClickListener { setTextFields(".") }
        AC_btn.setOnClickListener { math_operation.text = ""
                                    result_text.text = "" }
        Back_btn.setOnClickListener { val str: String =  math_operation.text.toString()
        if(str.isNotEmpty())
            math_operation.text = str.substring(0, str.length - 1)
        }
        Equals_btn.setOnClickListener { result_text.text = String_in_array(math_operation.text.toString()) }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState?.run {
            putString("KEY", math_operation.text.toString())
            putString("KEY2", result_text.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        math_operation.text = savedInstanceState?.getString("KEY")
        result_text.text = savedInstanceState?.getString("KEY2")
    }

    fun check_bracket(str: String): Boolean{
        var StackBracket = Stack<Char>()
        try{
        for (char in str){
            if(char == '(') StackBracket.push(char)
            if(char == ')') StackBracket.pop()
        }
            if(StackBracket.size == 0 ) return true
            else return  false
        }
        catch (e: Exception){
            return false
        }
    }


    fun setTextFields(str: String){



        if(math_operation.text.length < 1){
            if (str == "/" || str == "*" ||  str == "+" || str == "." || str == ")") return
            else math_operation.append(str)
        }
        else{
        var source_string: String = math_operation.text.toString()
        var change: String = source_string.substring(0, source_string.length - 1)
        var index_previous: Int = source_string.length - 1
        if((source_string[index_previous] == '/' || source_string[index_previous] == '*' || source_string[index_previous] == '-' || source_string[index_previous] == '+') && (str == "/" || str == "*" || str == "-" ||str == "+")) {
            math_operation.text = change
            math_operation.append(str)}
         else if((source_string.length > 1 && source_string[index_previous] == '0' && str in "0".."9" && (source_string[index_previous - 1] !in '0'..'9' && source_string[index_previous - 1] != '.' )) || (source_string.length == 1 && source_string[0] == '0'&& str in "0".."9") )
              //if(source_string[index_previous] == '0' && str in "0".."9") {}

        else if(source_string[index_previous] == '.' && str == ".")
        else if((source_string.length > 1 || source_string.length == 1) && (source_string[index_previous] in '0'..'9' || source_string[index_previous] == ')'|| source_string[index_previous] == '.') && str == "(")
        else if((source_string[index_previous] == '(' || source_string[index_previous] == '.') && (str !in "0".."9" ))
        else if(str == ")" &&  (source_string[index_previous] !in '0'..'9' && source_string[index_previous] !=')'))
        else math_operation.append(str)
        }
    }



    fun operation(b: Double, a: Double, op: String): Double {
        when (op) {
            "/" -> if(b == 0.0) return 9.9e1000 else return a / b
            "+" -> return a + b
            "*" -> return a * b
            "-" -> return a - b
        }
        return 0.0
    }

    fun Calculation(stroka: Array<String>): Double{
        if(stroka[1] == "-" && stroka[0] == ""){
            stroka.set(0, "0")}


        var NumberStack = Stack<Double>()
        var previos: String = "!"
        var index: Int = 0
        var OperationStack = Stack<String>()
        if (stroka.size == 2  && stroka[1] == "-") return 0.0
        //else if (stroka[1] == "-" && stroka.size == 3 && (stroka[2] != "/" && stroka[2] != "*" && stroka[2] != "-" && stroka[2] != "+")) return -stroka[2].toDouble()
        while (stroka[index] != "" && index != stroka.size) {
            if (stroka[index] != "/" && stroka[index] != "*" && stroka[index] != "-" && stroka[index] != "+" && stroka[index] != "." && stroka[index] != "(" && stroka[index] != ")" ) {
                NumberStack.push(stroka[index].toString().toDouble())
            } else {
                if("(" in OperationStack && NumberStack.size == 1 && stroka.size == 2) return NumberStack.pop()
                if (((previos == "*" || previos == "/") && (stroka[index] == "+" || stroka[index] == "-")) || ((previos == "+" || previos == "-") && (stroka[index] == "+" || stroka[index] == "-")) || ((previos == "/" || previos == "*") && (stroka[index] == "/" || stroka[index] == "*")) || (stroka[index] == ")")) {
                    if (previos == "(" && stroka[index] == ")") {
                        OperationStack.pop()
                        previos = OperationStack.pop()
                        OperationStack.push(previos)
                        continue
                    }
                    if ("(" !in OperationStack && stroka[index] == ")") {
                        index++
                        continue
                    }

                    NumberStack.push(
                        operation(
                            NumberStack.pop(),
                            NumberStack.pop(),
                            OperationStack.pop()
                        )
                    )

                    if (OperationStack.size == 0) {
                        OperationStack.push(stroka[index])
                        index++
                    }
                    previos = OperationStack.pop()
                    OperationStack.push(previos)
                    index--
                } else {
                    OperationStack.push(stroka[index])
                    previos = stroka[index]
                }

            }
            if (index == stroka.size - 1) break
            index++

        }
        while (NumberStack.size != 1) {
            NumberStack.push(operation(NumberStack.pop(), NumberStack.pop(), OperationStack.pop()))
            if(9.9e1000 in NumberStack) return 9.9e1000
        }
        return NumberStack.pop()
    }

    fun String_in_array(strokas : String): String {

        val stroka = Array(strokas.length + 1) { "" }
        val bracket: Boolean = check_bracket(strokas)
        stroka[0] = ""
        var i = 0
        var j = 0
        var last = 0
        var q = 0
        var check = false
        while (i < strokas.length - 0) {
            while (strokas[j] != '(' && strokas[j] != ')' && strokas[j] != '/' && strokas[j] != '*' && strokas[j] != '-' && strokas[j] != '+' && j != strokas.length - 0) {
                stroka[last] += strokas[j].toString()
                j++
                if (j == strokas.length - 0) {
                    check = true
                    break
                }
            }
            if (check == true) break
            if (strokas[j] == '(' || stroka[last - q] == ")") {
                stroka[last] += strokas[j].toString()
                last++
            } else {
                last++
                stroka[last] += strokas[j].toString()
                last++
            }
            //last++
            j++
            q = 1
            i = j
        }
        var error: String = resources.getString(R.string.Error_text);
        var zero: String = resources.getString(R.string.Zero_text);
        var count = 0
        var result: Double = 0.0
        for(i in stroka){
            if(i == "/" || i == "*" || i == "-" || i == "+" ) count++
        }
        if(count == 1 && stroka[0] != "" ) {
            result = operation(stroka[2].toDouble(), stroka[0].toDouble(), stroka[1])
            if (result == 9.9e1000) return zero.toString()
            if(result % 1 == 0.0) return result.toInt().toString()
            else return result.toString()
        }



        if(bracket == false) return error.toString()
        result =  Calculation(stroka)
        if (result == 9.9e1000) return zero.toString()
        else if (result % 1 == 0.0) return result.toInt().toString()
        else return result.toString()


    }

}
