package me.leon.ext.crypto

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import me.leon.encode.*
import me.leon.encode.base.*
import me.leon.ext.*

enum class EncodeType(val type: String, val defaultDict: String = "") : IEncode {
    Hex("hex") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.replace("""\\x|\s|0x|\\""".toRegex(), "").hex2ByteArray()

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) = bytes.toHex()
    },
    Base64("base64", BASE64_DICT) {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base64Decode(dict)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base64(dict)
    },
    Base16("base16", BASE16_DICT) {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base16Decode(dict)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base16(dict)
    },
    Base32("base32", BASE32_DICT) {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base32Decode(dict)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base32(dict)
    },
    Base36("base36(radix36)", BASE36_DICT) {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base36Decode(dict)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base36(dict)
    },
    Base58("base58(radix58)", BASE58_DICT) {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base58Decode(dict)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base58(dict)
    },
    Base58Check("base58Check", BASE58_DICT) {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base58CheckDecode(dict)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base58Check(dict)
    },
    Base85IPv6("base85_IPv6", BASE85_IPV6_DICT) {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base85Decode(dict.ifEmpty { BASE85_IPV6_DICT })

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base85(dict.ifEmpty { BASE85_IPV6_DICT })
    },
    Base62("base62", BASE62_DICT) {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base62Decode(dict)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base62(dict)
    },
    Base91("base91", BASE91_DICT) {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base91Decode(dict)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base91(dict)
    },
    Base92("base92", BASE92_DICT) {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base92Decode(dict, charset)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base92Encode(dict, charset)
    },
    Base64Url("base64Url", BASE64_URL_DICT) {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base64UrlDecode(dict)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base64Url(dict)
    },
    Base85("base85(ASCII85)", BASE85_DICT) {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base85Decode(dict)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base85(dict)
    },
    Z85("Z85(ZeroMQ)", Z85_DICT) {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base85Decode(dict.ifEmpty { Z85_DICT })

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base85(dict.ifEmpty { Z85_DICT })
    },
    Base100("base100", "") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base100Decode()

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base100()
    },
    Radix64("radix64") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.radix64Decode()

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.radix64()
    },
    Radix8("radix8") {
        override fun decode(encoded: String, dict: String, charset: String) = encoded.radix8Decode()

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) = bytes.radix8()
    },
    Radix10("radix10") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.radix10Decode()

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.radix10()
    },
    Radix32("radix32") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.radix32Decode()

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.radix32()
    },
    UrlEncode("urlencode") {
        override fun decode(encoded: String, dict: String, charset: String) =
            (URLDecoder.decode(encoded, charset.ifEmpty { "UTF-8" }) ?: "").toByteArray(
                Charset.forName(charset)
            )

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            URLEncoder.encode(bytes.toString(Charset.forName(charset)), charset.ifEmpty { "UTF-8" })
                ?.replace("+", "%20")
                ?: ""
    },
    Unicode("unicode") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.unicodeMix2String().toByteArray(Charset.forName(charset))

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.toString(Charset.forName(charset)).toUnicodeString()
    },
    Decimal("decimal(ASCII)") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.decimalDecode(charset)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.decimal(charset)
    },
    Octal("octal") {
        override fun decode(encoded: String, dict: String, charset: String) = encoded.octalDecode()
        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.octal(charset)
    },
    Binary("binary") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.binary2ByteArray()

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.toBinaryString()
    },
    Base45("base45", BASE45_DICT) {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.base45Decode(dict)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.base45(dict)
    },
    JsHexEncode("jsHex(shell code)") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.jsHexDecodeString().toByteArray(Charset.forName(charset))

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.toString(Charset.forName(charset)).toJsHexEncodeString()
    },
    JsOctalEncode("jsOctal") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.jsOctalDecodeString().toByteArray(Charset.forName(charset))

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.toString(Charset.forName(charset)).toJsOctalEncodeString()
    },
    Escape("escape") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.unescape(charset)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.escape(charset)
    },
    EscapeAll("escapeAll") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.unescape(charset)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.escapeAll(charset)
    },
    UuEncode("uuEncode") {
        override fun decode(encoded: String, dict: String, charset: String) = encoded.uuDecode(dict)
        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.uuEncode(dict)
    },
    XxEncode("xxEncode") {
        override fun decode(encoded: String, dict: String, charset: String) = encoded.xxDecode(dict)
        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.xxEncode(dict)
    },
    QuotePrintable("quotedPrintable") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.quotePrintableDecode(charset)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.quotePrintable(charset)
    },
    PunyCode("punyCode") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.punyCodeDecode(charset)

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.punyCode(charset)
    },
    HtmlEntity("htmlEntity") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.htmlEntity2String().toByteArray(Charset.forName(charset))

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.toString(Charset.forName(charset)).toHtmlEntity(isAll = true)
    },
    HexReverse("hexReverse") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.replace("""\\x|\s|0x|\\""".toRegex(), "").hexReverse2ByteArray()

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.toHexReverse()
    },
    UTF7("utf7") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.uft7Decode().toByteArray()

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.toString(charset.toCharset()).utf7()
    },
    UTF7_ALL("utf7 all") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.uft7Decode().toByteArray()

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.toString(charset.toCharset()).utf7(true)
    },
    UTF7_EXT("utf7(imap)") {
        override fun decode(encoded: String, dict: String, charset: String) =
            encoded.uft7ExtDecode().toByteArray()

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.toString(charset.toCharset()).utf7Ext()
    },
    MIX_HEX_OCT_BIN("HexOctBin") {
        override fun decode(encoded: String, dict: String, charset: String) = encoded.mixDecode()

        override fun encode2String(bytes: ByteArray, dict: String, charset: String) =
            bytes.mixEncode()
    },
}
