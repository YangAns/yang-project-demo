package com.yang.designpatternservice.templateMethod.service2;


import javax.validation.ConstraintViolation;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * <p>
 *  信息校验
 * </p>
 *
 * @author xiejiyang
 * @since 2024/1/30
 */
public interface ValidationService<T, E extends ValidationErrorVo> {
    List<E> validateData(List<T> dataList, Object param);


    /**
     * 获取校验错误信息
     * @param violations 校验结果
     * @param function 错误信息处理函数
     * @return 错误信息
     */
    default List<String> getValidateError(Set<ConstraintViolation<T>> violations, Function<ConstraintViolation<T>, String> function) {
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<T> violation : violations) {
            errorMessages.add(function.apply(violation));
        }
        return errorMessages;
    }


    /**
     * 校验并添加错误信息
     * @param condition 校验条件
     * @param message 错误信息
     * @param errorCount 错误数量
     * @param errorMessages 错误信息列表
     * @param errorFields 错误字段列表
     * @param fields 错误字段
     * @return 错误数量
     */
    default int checkAndAddErrorMessage(boolean condition, String message, int errorCount, List<String> errorMessages, List<String> errorFields, String... fields) {
        // 校验条件为true，则添加错误信息
        if (condition) {
            errorCount++;
            errorMessages.add(message);
            errorFields.addAll(Arrays.asList(fields));
        }
        return errorCount;
    }


    /**
     * 判断一个日期是否晚于另一个日期
     *
     * @param date 日期1
     * @param data2 日期2
     * @return  true:晚于;false:早于
     */
    default boolean isDate1AfterDate2(Long date, Long data2) {
        if (isDateNull(date) || isDateNull(data2)) {
            return false; // 如果其中一个时间为null，则无法判断
        }
        Instant birthInstant = Instant.ofEpochMilli(date);
        Instant retirementInstant = Instant.ofEpochMilli(data2);

        return birthInstant.isAfter(retirementInstant);
    }


    /**
     * 判断日期是否为空
     *
     * @param date 时间戳
     * @return  true:为空;false:不为空
     */
    default boolean isDateNull(Long date) {
        return date == null || date.equals(0L);
    }



    /**
     * 判断给定日期是否大于今天
     *
     * @param date  日期时间戳
     * @return  true:大于;false:小于
     */
    default boolean isDateGreaterThanToday(Long date) {
        if (isDateNull(date)) {
            return false;
        }
        LocalDate birthLocalDate = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        return birthLocalDate.isAfter(today);
    }

    default Class<?> determineValidationGroup(String param){
        return null;
    }


}
