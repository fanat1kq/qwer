package io.ylab.soi4.ideas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum of idea statuses. Describe the various stages of the idea's lifecycle during its
 * processing.
 */
@Getter
@AllArgsConstructor
public enum IdeaStatus {

    /**
     * Status when the idea requires modification.
     */
    MODIFICATION("Модификация"),

    /**
     * Status when the idea is being processed.
     */
    HANDLING("Обработка"),

    /**
     * Status when the idea is rejected.
     */
    REJECTED("Отклонена"),

    /**
     * Status when the idea is accepted.
     */
    ACCEPTED("Принята"),

    /**
     * Status when the idea is under consideration.
     */
    CONSIDERATION("Рассмотрение"),

    /**
     * Status when the idea is under examination.
     */
    EXAMINATION("Экспертиза");

    /**
     * The description of the idea status in Russian.
     */
    private final String description;
}