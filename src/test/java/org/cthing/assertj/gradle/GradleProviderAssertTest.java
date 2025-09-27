/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.gradle.api.Project;
import org.gradle.api.Transformer;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.testfixtures.ProjectBuilder;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.cthing.assertj.gradle.GradleProviderAssert.assertThat;


public class GradleProviderAssertTest {

    private Project project;

    @BeforeEach
    public void setup() {
        this.project = ProjectBuilder.builder().build();
    }

    @Test
    public void testContains() {
        final Provider<@NonNull String> provider1 = this.project.provider(() -> "test");
        assertThat(provider1).contains("test");

        final Property<@NonNull String> property1 = this.project.getObjects().property(String.class).convention("value1");
        assertThat(property1).contains("value1");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(property1).contains("value2"))
                .withMessage("Expecting provider to contain 'value2' but was 'value1'");

        final Property<@NonNull String> property2 = this.project.getObjects().property(String.class);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(property2).contains("value1"))
                .withMessage("Expecting provider to contain 'value1' but was empty");

        final MapProperty<@NonNull String, @NonNull String> mapProperty =
                this.project.getObjects()
                            .mapProperty(String.class, String.class)
                            .convention(Map.of("a", "b", "c", "d"));
        assertThat(mapProperty).contains(Map.of("a", "b", "c", "d"));
    }

    @Test
    public void testContainsInstanceOf() {
        final Provider<@NonNull String> provider1 = this.project.provider(() -> "test");
        assertThat(provider1).containsInstanceOf(String.class);

        final Property<@NonNull String> property1 = this.project.getObjects().property(String.class).convention("value1");
        assertThat(property1).containsInstanceOf(String.class);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(property1).containsInstanceOf(Integer.class))
                .withMessage("Expecting 'DefaultProperty' to contain an instance of 'java.lang.Integer' but contained an instance of 'java.lang.String'");

        final Property<@NonNull String> property2 = this.project.getObjects().property(String.class);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(property2).containsInstanceOf(String.class))
                .withMessage("Expecting 'property(java.lang.String, undefined)' to contain a value, but it was empty");
    }

    @Test
    public void testContainsSame() {
        final Integer value = 12345;
        final Provider<@NonNull Integer> provider1 = this.project.provider(() -> value);
        assertThat(provider1).containsSame(value);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(provider1).containsSame(12345))
                .withMessage("Expecting provider to contain value identical to '12345'");

        final Property<@NonNull Integer> property1 = this.project.getObjects().property(Integer.class);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(property1).containsSame(12345))
                .withMessage("Expecting provider to contain '12345' but was empty");
    }

    @Test
    public void testHasValue() {
        final Provider<@NonNull String> provider1 = this.project.provider(() -> "test");
        assertThat(provider1).hasValue("test");

        final Property<@NonNull String> property1 = this.project.getObjects().property(String.class).convention("value1");
        assertThat(property1).hasValue("value1");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(property1).hasValue("value2"))
                .withMessage("Expecting provider to contain 'value2' but was 'value1'");

        final Property<@NonNull String> property2 = this.project.getObjects().property(String.class);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(property2).hasValue("value1"))
                .withMessage("Expecting provider to contain 'value1' but was empty");

        final MapProperty<@NonNull String, @NonNull String> mapProperty =
                this.project.getObjects()
                            .mapProperty(String.class, String.class)
                            .convention(Map.of("a", "b", "c", "d"));
        assertThat(mapProperty).hasValue(Map.of("a", "b", "c", "d"));
    }

    @Test
    public void testHasValueSatisfyingConsumer() {
        final Provider<@NonNull String> provider1 = this.project.provider(() -> "test");
        assertThat(provider1).hasValueSatisfying(s -> Assertions.assertThat(s).isEqualTo("test"));

        final Property<@NonNull String> property1 = this.project.getObjects().property(String.class).convention("value1");
        assertThat(property1).hasValueSatisfying(s -> Assertions.assertThat(s).isEqualTo("value1"));

        final Property<@NonNull String> property2 = this.project.getObjects().property(String.class);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(property2)
                        .hasValueSatisfying(s -> Assertions.assertThat(s).isEqualTo("test")))
                .withMessage("Expecting 'property(java.lang.String, undefined)' to contain a value, but it was empty");
    }

    @Test
    public void testHasValueSatisfyingCondition() {
        final Condition<String> condition = new Condition<>("test"::equals, "testing");

        final Provider<@NonNull String> provider1 = this.project.provider(() -> "test");
        assertThat(provider1).hasValueSatisfying(condition);

        final Property<@NonNull String> property1 = this.project.getObjects().property(String.class).convention("test");
        assertThat(property1).hasValueSatisfying(condition);

        final Property<@NonNull String> property2 = this.project.getObjects().property(String.class);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(property2).hasValueSatisfying(condition))
                .withMessage("Expecting 'property(java.lang.String, undefined)' to contain a value, but it was empty");
    }

    @Test
    public void testIsPresent() {
        final Provider<@NonNull String> provider1 = this.project.provider(() -> "test");
        assertThat(provider1).isPresent();

        final Property<@NonNull String> property1 = this.project.getObjects().property(String.class).convention("value1");
        assertThat(property1).isPresent();

        final Property<@NonNull String> property2 = this.project.getObjects().property(String.class);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(property2).isPresent())
                .withMessage("Expecting 'property(java.lang.String, undefined)' to contain a value, but it was empty");
    }

    @Test
    public void testIsEmpty() {
        final Provider<@NonNull String> provider1 = this.project.provider(() -> "test");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(provider1).isEmpty())
                .withMessage("Expecting provider to be empty but contains 'provider(?)'");

        final Property<@NonNull String> property1 = this.project.getObjects().property(String.class).convention("value1");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(property1).isEmpty())
                .withMessage("Expecting provider to be empty but contains 'property(java.lang.String, fixed(class java.lang.String, value1))'");

        final Property<@NonNull String> property2 = this.project.getObjects().property(String.class);
         assertThat(property2).isEmpty();
    }

    @Test
    public void testGet() {
        final Provider<@NonNull String> provider1 = this.project.provider(() -> "test");
        assertThat(provider1).get().isEqualTo("test");

        final Property<@NonNull String> property1 = this.project.getObjects().property(String.class).convention("value1");
        assertThat(property1).get().isEqualTo("value1");

        final Property<@NonNull String> property2 = this.project.getObjects().property(String.class);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(property2).get().isNotNull())
                .withMessage("Expecting 'property(java.lang.String, undefined)' to contain a value, but it was empty");
    }

    @Test
    public void testGetWithFactory() {
        final Provider<@NonNull String> provider1 = this.project.provider(() -> "test");
        assertThat(provider1).get(InstanceOfAssertFactories.STRING).isEqualTo("test");

        final Provider<@NonNull List<String>> provider2 = this.project.provider(() -> List.of("a", "b"));
        assertThat(provider2).get(InstanceOfAssertFactories.LIST).containsExactly("a", "b");

        final Property<@NonNull File> property1 = this.project.getObjects()
                                                     .property(File.class)
                                                     .convention(new File("build/a.txt"));
        assertThat(property1).get(InstanceOfAssertFactories.FILE).hasName("a.txt");

        final Property<@NonNull String> property2 = this.project.getObjects().property(String.class);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(property2).get(InstanceOfAssertFactories.STRING).isNotNull())
                .withMessage("Expecting 'property(java.lang.String, undefined)' to contain a value, but it was empty");
    }

    @Test
    public void testMap() {
        final Provider<@NonNull String> provider1 = this.project.provider(() -> "test1");
        assertThat(provider1).map(s -> "test2").contains("test2");

        final Property<@NonNull String> property1 = this.project.getObjects().property(String.class).convention("value1");
        assertThat(property1).map(s -> "value2").contains("value2");

        final Property<@NonNull String> property2 = this.project.getObjects().property(String.class);
        assertThat(property2).map(s -> null).isEmpty();
    }

    @Test
    public void testFlatMap() {
        final Provider<@NonNull String> provider1 = this.project.provider(() -> "test1");
        final Provider<@NonNull String> provider2 = this.project.provider(() -> "test2");
        assertThat(provider1).flatMap((Transformer<Provider<String>, String>)s -> provider2).contains("test2");
    }
}
