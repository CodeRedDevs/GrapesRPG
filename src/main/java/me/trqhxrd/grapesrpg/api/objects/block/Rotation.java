package me.trqhxrd.grapesrpg.api.objects.block;

public enum Rotation {
    NORTH(Direction.NORTH),
    SOUTH(Direction.SOUTH),
    EAST(Direction.EAST),
    WEST(Direction.WEST),
    UP(null),
    DOWN(null);

    private final Direction direction;

    Rotation(Direction direction) {
        this.direction = direction;
    }

    public Direction toDirection() {
        return this.direction;
    }
}
