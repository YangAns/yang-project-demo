
package com.yang.common.utils;

import cn.hutool.core.collection.ListUtil;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class YListUtils extends ListUtil {

//    public static void main(String[] args) {
//        List<String> abc = new ArrayList<>();
//        abc.add("a");
//        abc.add("c");
//        abc.add("b");
//
//        List<String> abc2 = new ArrayList<>();
//        abc2.add("a");
//        abc2.add("b");
//        abc2.add("c");
//        abc2.add("d");
//
//        System.out.println(isSameList(abc, abc2));
//    }

//    public static <T> boolean isSameList(List<T> newList, List<T> oldList) {
//        CompareContainer<T> container = new CompareContainer<>(newList, oldList);
//        List<T> insertModels = container.getInsertModels();
//        List<T> deleteModels = container.getDeleteModels();
//        return insertModels.isEmpty() && deleteModels.isEmpty();
//    }

    public static <T> int sumField(List<T> ts, Function<T, Integer> intField) {
        if (isEmptyList(ts)) {
            return 0;
        }
        int count = 0;
        for (T t : ts) {
            Integer apply = intField.apply(t);
            if (null != apply) {
                count += apply;
            }
        }
        return count;
    }

    public static <T> int countCondition(List<T> ts, Predicate<T> testFunction) {
        if (null == ts) {
            return 0;
        }
        int count = 0;
        for (T t : ts) {
            if (YStrUtils.isNotNull(t) && testFunction.test(t)) {
                count++;
            }
        }
        return count;
    }

//    void doCondition(List<T> ts, Predicate<T> testFunction, Consumer<T> doFun) {
//        if (null == ts || ts.isEmpty()) {
//            return;
//        }
//        for (T t : ts) {
//            if (null != t && testFunction.test(t)) {
//                doFun.accept(t);
//            }
//        }
//    }


    public static <T> List<T> collectCondition(List<T> ts, Predicate<T> testFunction) {
        if (null == ts || ts.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> result = new ArrayList<>();
        for (T t : ts) {
            if (null != t && testFunction.test(t)) {
                result.add(t);
            }
        }
        return result;
    }


    public static <T> boolean allMatch(List<T> ts, Predicate<T> testFunction) {
        if (null == ts) {
            return false;
        }
        for (T t : ts) {
            if (YStrUtils.isNull(t) || !testFunction.test(t)) {
                return false;
            }
        }
        return true;
    }


    public static <T> boolean anyMatch(List<T> ts, Predicate<T> testFunction) {
        if (null == ts) {
            return false;
        }
        for (T t : ts) {
            if (YStrUtils.isNotNull(t) && testFunction.test(t)) {
                return true;
            }
        }
        return false;
    }

    public static <T> T anyMatchItem(List<T> ts, Predicate<T> testFunction) {
        if (null == ts) {
            return null;
        }
        for (T t : ts) {
            if (YStrUtils.isNotNull(t) && testFunction.test(t)) {
                return t;
            }
        }
        return null;
    }

    public static List<String> clearRepeatElement(List<String> keys) {
        return clearRepeatElement(keys, k -> k);
    }

    public static <T> List<T> clearRepeatElement(List<T> ts, Function<T, String> targetFunction) {
        if (null == ts || ts.size() == 0) {
            return new ArrayList<>();
        }
        List<T> noRepeatList = new ArrayList<>();
        List<String> existsKeys = new ArrayList<>();
        for (T t : ts) {
            if (null == t) {
                continue;
            }
            String key = targetFunction.apply(t);
            if (YStrUtils.isNotNull(key) && !existsKeys.contains(key)) {
                noRepeatList.add(t);
                existsKeys.add(key);
            }
        }
        return noRepeatList;
    }

    public static List<String> str2ListWithSpecialReg(String str) {
        if (YStrUtils.isNull(str)) {
            return new ArrayList<>();
        }
        List<String> specialRegs = toList("\\|", ",", "#", "，", " ");

        for (String reg : specialRegs) {
            String[] split = str.split(reg);
            if (split.length == 1) {
                continue;
            }
            List<String> list = new ArrayList<>();
            for (String string : split) {
                if (YStrUtils.isNotNull(string) && !list.contains(string)) {
                    list.add(string);
                }
            }
            int size = list.size();
            if (size > 1) {
                return list;
            }
        }
        return YListUtils.toList(str);
    }

    // 字符串转List
    public static List<String> str2List(String str, String reg) {
        if (YStrUtils.isNull(str)) {
            return new ArrayList<>();
        }
        String[] split = str.split(reg);
        List<String> list = new ArrayList<>();
        for (String string : split) {
            if (YStrUtils.isNotNull(string) && !list.contains(string)) {
                list.add(string);
            }
        }
        return list;
    }

    // 字符串转集合
    public static List<String> str2List(String str) {
        return str2List(str, ",");
    }

    // 字符串转long集合
    public static List<Long> str2LongList(String str) {
        List<String> str2List = str2List(str, ",");
        return list2list(str2List, s -> {
            if (YStrUtils.isNotNull(s)) {
                try {
                    return Long.parseLong(s);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });
    }

    //字符串转int集合
    public static List<Integer> str2IntList(String str) {
        List<String> str2List = str2List(str, ",");
        return list2list(str2List, s -> {
            if (YStrUtils.isNotNull(s)) {
                try {
                    return Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });
    }

    public static <T> void sortLong(List<T> ts, Function<T, Long> getSort, boolean isDesc) {
        ts.sort((t1, t2) -> {
            Long s1 = getSort.apply(t1);
            Long s2 = getSort.apply(t2);
            if (isDesc) {
                s1 = null == s1 ? Long.MIN_VALUE : s1;
                s2 = null == s2 ? Long.MIN_VALUE : s2;
                return s2.compareTo(s1);
            } else {
                s1 = null == s1 ? Long.MAX_VALUE : s1;
                s2 = null == s2 ? Long.MAX_VALUE : s2;
                return s1.compareTo(s2);
            }
        });
    }

    public static <T> void sortInteger(List<T> ts, Function<T, Integer> getSort, boolean isDesc) {
        ts.sort((t1, t2) -> {
            Integer s1 = getSort.apply(t1);
            Integer s2 = getSort.apply(t2);
            if (isDesc) {
                s1 = null == s1 ? Integer.MIN_VALUE : s1;
                s2 = null == s2 ? Integer.MIN_VALUE : s2;
                return s2.compareTo(s1);
            } else {
                s1 = null == s1 ? Integer.MAX_VALUE : s1;
                s2 = null == s2 ? Integer.MAX_VALUE : s2;
                return s1.compareTo(s2);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] list2Arr(List<T> ts) {
        if (null == ts || ts.size() == 0) {
            return null;
        }
        Class<?> class1 = ts.get(0).getClass();
        T[] array = (T[]) Array.newInstance(class1, ts.size());
        for (int i = 0; i < ts.size(); i++) {
            array[i] = ts.get(i);
        }
        return array;
    }

    public static <T> List<T> set2list(Set<T> ts) {
        if (null == ts || ts.isEmpty()) {
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<>();
        for (T t : ts) {
            if (YStrUtils.isNotNull(t)) {
                list.add(t);
            }
        }
        return list;
    }

    public static <T> Set<T> list2Set(List<T> ts) {
        if (isEmptyList(ts)) {
            return new HashSet<>();
        }
        Set<T> set = new HashSet<>();
        for (T t : ts) {
            if (YStrUtils.isNotNull(t)) {
                set.add(t);
            }
        }
        return set;
    }

    public static <O, T> List<T> list2list(List<O> os, Function<O, ? extends T> target) {
        return list2list(os, target, false);
    }

    public static <O, T> List<T> list2list(List<O> os, Function<O, ? extends T> target, boolean needClearRepeat) {
        List<T> ts = new ArrayList<>();
        if (null == os || os.size() == 0) {
            return ts;
        }
        os.forEach(o -> {
            T apply = target.apply(o);
            if (YStrUtils.isNotNull(apply)) {
                if (needClearRepeat) {
                    if (!ts.contains(apply)) {
                        ts.add(apply);
                    }
                } else {
                    ts.add(apply);
                }
            }
        });

        return ts;
    }

    public static <O, T> List<T> array2list(O[] os, Function<O, ? extends T> target) {
        List<T> ts = new ArrayList<>();
        if (null == os || os.length == 0) {
            return ts;
        }
        for (O o : os) {
            T apply = target.apply(o);
            if (null != apply) {
                ts.add(apply);
            }
        }
        return ts;
    }

    public static <O> List<O> array2list(O[] os) {
        return array2list(os, o -> o);
    }

    public static <O> Set<O> array2Set(O[] os) {
        return array2Set(os, o -> o);
    }

    public static <O, T> Set<T> array2Set(O[] os, Function<O, ? extends T> target) {
        Set<T> ts = new HashSet<>();
        if (null == os) {
            return ts;
        }
        for (O o : os) {
            T apply = target.apply(o);
            if (null != apply) {
                ts.add(apply);
            }
        }
        return ts;
    }

    public static <T> Map<String, List<T>> list2map(List<T> ts, Function<T, String> targetFunction) {
        return groupList(ts, targetFunction);
    }

    public static <T, K> Map<K, List<T>> groupList(List<T> ts, Function<? super T, ? extends K> classifier) {
        if (null == classifier) {
            return new HashMap<>();
        }
        return ts.stream().filter(t -> YStrUtils.isNotNull(classifier.apply(t)))
                .collect(Collectors.groupingBy(classifier));
    }

    public static <T> List<T> findTopRecord(List<T> ts, int top) {
        List<T> tops = new ArrayList<>();
        if (null == ts) {
            return tops;
        }
        for (T t : ts) {
            if (null != t && tops.size() < top) {
                tops.add(t);
            }
        }
        return tops;
    }

    public static <T, K> Map<String, List<K>> groupList(List<T> sourceList, Function<? super T, String> keyFun,
                                                        Function<? super T, K> fieldFun) {
        Map<String, List<K>> map = new HashMap<>();
        if (null == sourceList || sourceList.isEmpty()) {
            return map;
        }
        sourceList.forEach(source -> {
            List<K> value = map.computeIfAbsent(keyFun.apply(source), k -> new ArrayList<>());
            value.add(fieldFun.apply(source));
        });
        return map;
    }

    public static <T> Map<String, T> groupModel(List<T> ts, Function<? super T, String> keyFun) {
        Map<String, T> map = new HashMap<>();
        if (null == ts || ts.size() == 0) {
            return map;
        }
        ts.forEach(t -> {
            if (null != t) {
                String key = keyFun.apply(t);
                if (YStrUtils.isNotNull(key) && !map.containsKey(key)) {
                    map.put(key, t);
                }
            }
        });
        return map;
    }

    public static <T> Map<String, String> groupField(List<T> ts, Function<? super T, String> keyFunction,
                                                     Function<? super T, String> fieldFun) {
        Map<String, String> map = new HashMap<>();
        ts.forEach(t -> {
            String key = keyFunction.apply(t);
            String fieldValue = fieldFun.apply(t);
            if (YStrUtils.isAllNotNull(key, fieldValue)) {
                map.put(key, fieldValue);
            }
        });
        return map;
    }

    public static <T> Map<String, Long> groupFieldLong(List<T> ts, Function<? super T, String> keyFunction,
                                                       Function<? super T, Long> fieldFun) {
        Map<String, Long> map = new HashMap<>();
        ts.forEach(t -> {
            String key = keyFunction.apply(t);
            Long fieldValue = fieldFun.apply(t);
            if (YStrUtils.isAllNotNull(key, fieldValue)) {
                map.put(key, fieldValue);
            }
        });
        return map;
    }

    public static <T> Map<String, Integer> groupFieldInt(List<T> ts, Function<? super T, String> keyFunction,
                                                         Function<? super T, Integer> fieldFun) {
        Map<String, Integer> map = new HashMap<>();
        ts.forEach(t -> {
            String key = keyFunction.apply(t);
            Integer fieldValue = fieldFun.apply(t);
            if (YStrUtils.isAllNotNull(key, fieldValue)) {
                map.put(key, fieldValue);
            }
        });
        return map;
    }

    public static <T, F> List<F> groupField2List(List<T> ts, Function<? super T, F> field) {
        if (null == ts || ts.size() == 0) {
            return new ArrayList<>();
        }
        return ts.stream().filter(t -> YStrUtils.isNotNull(field.apply(t))).map(field)
                .collect(Collectors.toList());
    }

    public static <T, E> Map<String, List<E>> groupFields(List<T> ts, Function<? super T, String> keyFunction,
                                                          Function<? super T, E> fieldFun) {
        Map<String, List<E>> map = new HashMap<>();
        for (T t : ts) {
            String key = keyFunction.apply(t);
            List<E> list = map.containsKey(key) ? map.get(key) : new ArrayList<>();
            list.add(fieldFun.apply(t));
            map.put(key, list);
        }
        return map;
    }

    public static <T> List<String> collectField(List<T> ts, Function<? super T, String> keyFunction) {
        return list2list(ts, keyFunction::apply);
    }

    public static <T> List<String> collectFieldInt(List<T> ts, Function<? super T, Integer> keyFunction) {
        if (null == ts || ts.size() == 0) {
            return new ArrayList<>();
        }
        List<String> strings = new ArrayList<>();
        for (T t : ts) {
            Integer apply = keyFunction.apply(t);
            if (YStrUtils.isNotNull(apply)) {
                strings.add(String.valueOf(apply));
            }
        }
        return strings;
    }

    public static <T> Set<String> collectFieldSet(List<T> ts, Function<? super T, String> keyFunction) {
        if (null == ts || ts.size() == 0) {
            return new HashSet<>();
        }
        Set<String> strings = new HashSet<>();
        for (T t : ts) {
            String apply = keyFunction.apply(t);
            if (YStrUtils.isNotNull(apply)) {
                strings.add(apply);
            }
        }
        return strings;

    }

    public static <T> List<List<T>> subList2SameCount(List<T> list, int num) {
        int size = list.size();
        int forTimes = size / num;
        int more = size % num;
        forTimes = more > 0 ? ++forTimes : forTimes;
        int count = 0;
        List<List<T>> lists = new ArrayList<>();
        Iterator<T> iterator = list.iterator();
        List<T> ts;
        for (int i = 0; i < forTimes; i++) {
            ts = new ArrayList<>();
            while (iterator.hasNext()) {
                if (count > size || ts.size() == num) {
                    break;
                }
                ts.add(iterator.next());
                iterator.remove();
                count++;
            }
            lists.add(ts);
        }
        return lists;
    }

    /*
     * 判断source列表中是否包含target列表中的元素
     *
     * @param source
     * @param target
     * @return
     */
    public static <T> Boolean checkExist(List<T> source, List<T> target) {
        boolean isExist = false;
        for (T item : source) {
            if (target.contains(item)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    @SafeVarargs
    public static <T> List<T> unionList(List<T>... ts) {
        if (null == ts || ts.length == 0) {
            return Collections.emptyList();
        }
        List<T> result = new ArrayList<>();
        for (List<T> t : ts) {
            if (isEmptyList(t)) {
                continue;
            }
            for (T item : t) {
                if (YStrUtils.isNotNull(item)) {
                    result.add(item);
                }
            }
        }
        return result;
    }


    /*
     * 校验list
     * @param list
     * @return
     */
    public static <T> boolean isNotEmptyList(List<T> list) {
        return !isEmptyList(list);
    }

    /*
     * 校验list
     * @param list
     * @return
     */
    public static <T> boolean isEmptyList(List<T> list) {
        boolean isEmpty = null == list || list.isEmpty();
        if (isEmpty) {
            return true;
        }
        // 集合里面的每一项都是空
        for (T t : list) {
            if (null != t) {
                return false;
            }
        }
        return true;
    }

    /*
     * 校验list
     * @param list
     * @return
     */
    public static <T> boolean isSingletonList(List<T> list) {
        return null != list && list.size() == 1;
    }
}
