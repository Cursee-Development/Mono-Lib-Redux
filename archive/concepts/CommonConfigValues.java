package com.cursee.monolib.core;

import java.util.List;

public class CommonConfigValues {

    public static boolean enable_debugging = false;
    public static boolean enable_jar_verification = true;

    public static String        test_string = "[default, value.test]";
    public static int           test_int = 3_000;
    public static float         test_float = 3.013f;
    public static boolean       test_boolean = false;

    public static List<String>  test_string_list = List.of("[1]", ",2 [3.f3.4", "\"\" 21.f");
    public static List<Integer> test_int_list = List.of(2_0, 200, 42_000, 0);
    public static List<Float>   test_float_list = List.of(2_01.0f, 21f, 2f, 0f);
    public static List<Boolean> test_boolean_list = List.of(true, false, true, true);
}
