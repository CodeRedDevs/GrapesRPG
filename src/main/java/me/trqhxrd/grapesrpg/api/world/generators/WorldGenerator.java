package me.trqhxrd.grapesrpg.api.world.generators;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import java.util.Random;

/**
 * This Class is responsible for generating the world in which you can farm resources.
 */
@SuppressWarnings("NullableProblems")
public class WorldGenerator extends ChunkGenerator {

    /**
     * Just a variable for using the same height over multiple chunks.
     */
    private int height = 50;

    /**
     * This method generates the world with some input Objects.
     *
     * @param world  The World Object in which a chunk will be generated.
     * @param random The Random calculator for making the world go up and down for example.
     * @param chunkX The X Coordinate of the chunk.
     * @param chunkZ Thy Y Coordinate of the chunk.
     * @param b      The BiomeGrid, which contains all information about the biomes in this World.
     * @return The finished ChunkData.
     */
    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid b) {
        ChunkData chunk = super.createChunkData(world);
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(world.getSeed(), 8);
        generator.setScale(.005D);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                height = (int) (generator.noise(chunkX * 16 + x, chunkZ * 16 + z, .5, .5) * 15 + 50);
                b.setBiome(x, height, z, Biome.PLAINS);
                for (int i = 0; i <= height; i++) chunk.setBlock(x, i, z, Material.STONE);
                for (int i = 0; i <= 3; i++) chunk.setBlock(x, height - i, z, Material.DIRT);
                chunk.setBlock(x, height, z, Material.GRASS_BLOCK);
                chunk.setBlock(x, 0, z, Material.BEDROCK);
                if (random.nextBoolean()) chunk.setBlock(x, height + 1, z, Material.GRASS);
            }
        }
        return chunk;
    }
}
