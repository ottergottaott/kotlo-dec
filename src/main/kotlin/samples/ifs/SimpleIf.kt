package samples.ifs

fun ifElse(x: Int): Int {
    var res = x

    if (x < 10) {
        if (x > 4) {
            res = 10
        }

        res *= x
    } else {
        res = x / 10
    }

//    print(res)
    return res
}


fun ifBody(x: Int): Int {
    var res = x

    if (x < 10) {
        res = x + 5
    } else {
        if (x > 4) {
            res = x * 4
        }
    }

    if (x < 10) {
        res = x * 5
    } else if (x > 4) {
        res = x * 4
    }

//    print(res)
    return res
}

fun ifSimpleBody(x: Int) {
    var res = 10
    if (x < 10) {
        res = x + 5
    }
}

fun ifComplicatedCondition(x: Int, y: Int): Int {
    var res = 0

    if (x > 0 && y > 0) {
        res = x * y
    } else if (x * 3 < 1) {
        res = x + y
    } else if (x > 20) {
        res = x - y
    } else {
        res = 5
    }

//    print(res)
    return res
}

fun ifMoreComplicatedCondition(x: Int, y: Int): Int {
    var res = 0
    var notRes = 10

    if ((x * 10) > 0 && (y + x) > 0) {
        res = x * y
    } else {
        res = x + y
    }

    if ((x * 10) > 0 && (y + x) > 0) {
        res = x * y
    } else {
        res = x + y
    }

//    print(res)
    return res + 10
}

fun ifSuperComplicatedCondition(x: Int, y: Int): Int {
    var res: Int

    if ((x * 10) > 0 && (y + x) > 0) {
        res = x * y

        if (res > 16) {
            res = 16
        } else {
            res = 5
        }

    } else {
        res = x + y
    }

//    print(res)
    return res
}
