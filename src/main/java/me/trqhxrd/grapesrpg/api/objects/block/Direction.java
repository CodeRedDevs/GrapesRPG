package me.trqhxrd.grapesrpg.api.objects.block;

public enum Direction {
    NORTH(Rotation.NORTH),
    SOUTH(Rotation.SOUTH),
    EAST(Rotation.EAST),
    WEST(Rotation.WEST);

    private final Rotation rotation;

    Direction(Rotation rotation) {
        this.rotation = rotation;
    }

    public Rotation toRotation() {
        return rotation;
    }
}
