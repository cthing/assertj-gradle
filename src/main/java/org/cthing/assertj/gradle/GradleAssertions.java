/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import org.gradle.api.Project;
import org.gradle.api.provider.Provider;


/**
 * Provides access to all Gradle assertions.
 */
public final class GradleAssertions {

    private GradleAssertions() {
    }

    /**
     * Creates the assertion for the specified Gradle project.
     *
     * @param project  Gradle project to test
     * @return This assertion
     */
    public static GradleProjectAssert assertThat(final Project project) {
        return GradleProjectAssert.assertThat(project);
    }

    /**
     * Creates the assertion for the specified Gradle provider.
     *
     * @param <T> Type of the value contained in the {@link Provider}
     * @param provider  Gradle provider to test
     * @return This assertion
     */
    public static <T> GradleProviderAssert<T> assertThat(final Provider<T> provider) {
        return GradleProviderAssert.assertThat(provider);
    }
}
