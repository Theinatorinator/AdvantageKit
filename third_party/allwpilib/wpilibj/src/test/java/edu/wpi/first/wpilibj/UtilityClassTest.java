// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.wpilibj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class UtilityClassTest<T> {
  private final Class<T> m_clazz;

  protected UtilityClassTest(Class<T> clazz) {
    m_clazz = clazz;
  }

  @Test
  public void singleConstructorTest() {
    assertEquals(1, m_clazz.getDeclaredConstructors().length, "More than one constructor defined");
  }

  @Test
  public void constructorPrivateTest() {
    Constructor<?> constructor = m_clazz.getDeclaredConstructors()[0];

    assertFalse(constructor.canAccess(null), "Constructor is not private");
  }

  @Test
  public void constructorReflectionTest() {
    Constructor<?> constructor = m_clazz.getDeclaredConstructors()[0];
    constructor.setAccessible(true);
    assertThrows(InvocationTargetException.class, constructor::newInstance);
  }

  @TestFactory
  Stream<DynamicTest> publicMethodsStaticTestFactory() {
    return Arrays.stream(m_clazz.getDeclaredMethods())
        .filter(method -> Modifier.isPublic(method.getModifiers()))
        .map(
            method ->
                dynamicTest(
                    method.getName(), () -> assertTrue(Modifier.isStatic(method.getModifiers()))));
  }
}
