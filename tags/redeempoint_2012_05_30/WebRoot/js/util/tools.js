/**
 * 将数字格式化为金额格式
 * 12345.678 -> 12,345.678
 * @param {Object} s 要处理的数字
 * @param {Object} n 保留数字的小树位数, 自动四舍五入
 */
function fmoney(s, n){
    n = n > 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
    t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
}

/**
 * 还原数字格式
 * @param {Object} s
 */
function rmoney(s){
    return parseFloat(s.replace(/[^\d\.-]/g, ""));
}
