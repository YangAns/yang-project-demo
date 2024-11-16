
package com.yang.common.utils;

import cn.hutool.core.util.StrUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("unused")
public class YStrUtils extends StrUtil {

//    public static void main(String[] args) {
//        String html="<p style=\\\"text-indent: 2em; line-height: 2;\\\">关于取消按隶属关系划分金川公司企业所得税收入政策的建议</p>\n" +
//                "<p style=\\\"text-indent: 2em; line-height: 2;\\\">案由：金昌市缘矿兴企，因企设市，是典型的资源型工矿城市。受资源开采和发展空间限制，要实现更高质量、更有效率、更加公平、更可持续、更为安全的发展，必须深入贯彻落实&ldquo;三新一高&rdquo;要求，以&ldquo;2+4&rdquo;产业链培育提升为抓手，推动传统产业全面延伸、新兴产业蓬勃发展，构建现代产业体系。</p>\n" +
//                "<p style=\\\"text-indent: 2em; line-height: 2;\\\">近年来，金昌市持续加强对金川集团公司的协调服务，积极承接企业社会化职能、支持三供一业移交、承办企业科技攻关大会、助力国家级重点实验室重组，企业工业总产值、利润总额均创历史新高，作为全省唯一上榜企业，连续四年入围世界500强榜单。但是，作为服务重大战略物资生产企业驻地的金昌市，享受的红利及其贡献与企业发" +
//                "展不相匹配。据统计，2021年金川公司缴纳税收44.11亿元（含进口货物增值税11.4亿元），同比增长29%。按各税种分享比例计算，市县级分享金川公司税收收入11.68亿元，仅占其税收总量的26.47%，较市域其他企业税收贡献率42.42%低15.95个百分点。2022年金川公司税收贡献率进一步降低，前7个月，公司缴纳税收51.05亿元（含进口货物增值税7.4亿元），同比增长98.8%，市县级分享金川公司税收收入8.7亿元，税收贡献率降至17.05%，绝大多数由中央和省级分享。</p>\n" +
//                "<p style=\\\"text-indent: 2em; line-height: 2;\\\">2000年12月，金川公司划归甘肃省管理。2003年实行所得税收入改革后，根据省与市财政体制实施方案，金川公司企业所得税的分享比例为中央60%，省级40%，我市不参与分享，该政策一直沿用至今。酒钢公司、白银公司、靖远煤电等重点省属企业所得税均按照中央60%,省级20%，市州20%比例分享。</p>\n" +
//                "<p style=\\\"text-indent: 2em; line-height: 2;\\\">建议：按照《国务院办公厅关于进一步推进省以下财政体制改革工作的指导意见》中&ldquo;除按规定上缴财政的国有资本经营收益外，逐步减少直至取消按企业隶属关系划分政府间收入的做法&rdquo;的规定，建议省财政取消按隶属关系对金川公司企业所得税收入划分政策，实行统一的6:2:2分享比例。</p>";
//        String s = cleanHtml(html);
//        System.out.println(s);
//    }

//    public static String cleanHtml(String text) {
//        if (ZYStrUtils.isNull(text)) {
//            return text;
//        }
//        return Jsoup.parse(text).text();
//    }

    // 字符串转char数组
    public static List<String> strToChars(String str) {
        List<String> strList = new ArrayList<>();
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            strList.add(Character.toString(c));
        }
        return strList;
    }


    // 连接字符串
    public static <E> String join(Collection<E> list) {
        return join(list, ",");
    }

    // 连接字符串
    public static <E> String join(Collection<E> list, String separator) {
        if (null == list || list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<E> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            E next = iterator.next();
            if (isNull(next)) {
                continue;
            }
            sb.append(next.toString());
            if (i < list.size() - 1) {
                sb.append(separator);
            }
            i++;
        }
        return sb.toString();

    }

    // 首字母转小写
    public static String firstLowerCase(String str) {
        char[] charArray = str.toCharArray();
        charArray[0] = (char) (charArray[0] + 32);
        return new String(charArray);
    }

    // 首字母转大写
    public static String firstUpperCase(String str) {
        try {
            char[] charArray = str.toCharArray();
            charArray[0] = (char) (charArray[0] - 32);
            return new String(charArray);
        } catch (Exception e) {
            return str;
        }
    }

    // 手机加星号
    public static String phoneWithStar(String phone) {
        if (isNull(phone)) {
            return null;
        }
        if (phone.length() < 7) {
            return phone;
        }
        char[] charArray = phone.toCharArray();
        for (int i = 3; i < 7; i++) {
            charArray[i] = "*".charAt(0);
        }
        return new String(charArray);
    }

    // 判断传入对象有任一一个不为空
    public static boolean isAnyNotNull(Object... objs) {
        if (null == objs || objs.length == 0) {
            return false;
        }
        for (Object obj : objs) {
            if (isNotNull(obj)) {
                return true;
            }
        }
        return false;
    }

    // 判断传入对象是否全部不为空
    public static boolean isAllNotNull(Object... objs) {
        if (null == objs || objs.length == 0) {
            return false;
        }
        for (Object obj : objs) {
            if (isNull(obj)) {
                return false;
            }
        }
        return true;
    }

    // 判断传入对象有任一一个为空
    public static boolean isAnyNull(Object... objs) {
        if (null == objs || objs.length == 0) {
            return true;
        }
        for (Object obj : objs) {
            if (isNull(obj)) {
                return true;
            }
        }
        return false;
    }

    // 判断传入对象是否全部为空
    public static boolean isAllNull(Object... objs) {
        if (null == objs || objs.length == 0) {
            return true;
        }
        for (Object obj : objs) {
            if (isNotNull(obj)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isNull(Object obj) {
        if (null == obj) {
            return true;
        }
        if (obj instanceof Collection) {
            Collection<?> collection = (Collection<?>) obj;
            return collection.size() == 0;
        }

        if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            return map.isEmpty();
        }

        String str = String.valueOf(obj);
        if (null == str) {
            return true;
        }

        int strLen = str.length();
        if (strLen == 0) {
            return true;
        }
        String[] regs = {"[]", "{}", "undefined", "null"};
        for (String reg : regs) {
            if (reg.equals(str)) {
                return true;
            }
        }
        for (int i = 0; i < strLen; ++i) {
            if (!(Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    // 拼接成字符串
    public static String spellString(Object... objs) {
        if (null == objs || objs.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Object obj : objs) {
            stringBuilder.append(obj);
        }
        return stringBuilder.toString();
    }

    /**
     * 下划线转驼峰法
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underlineToCamel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camelToUnderline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toLowerCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }
}
