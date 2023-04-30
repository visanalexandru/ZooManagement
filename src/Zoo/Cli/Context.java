package Zoo.Cli;

/**
 * Certain menus require a context. For example, the habitat menu renders
 * different options when we inspect unused habitats, compared to when
 * we are inspecting used habitats.
 */
public enum Context {
    HABITAT_USED,
    HABITAT_UNUSED,

    HABITAT_STORE,

    ANIMAL_USED,
    ANIMAL_UNUSED,
    ANIMAL_STORE

}
