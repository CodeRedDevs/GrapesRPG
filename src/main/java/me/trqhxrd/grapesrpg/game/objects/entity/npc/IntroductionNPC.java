package me.trqhxrd.grapesrpg.game.objects.entity.npc;

import me.trqhxrd.grapesrpg.api.objects.entity.npc.GrapesStoryNPC;
import me.trqhxrd.grapesrpg.api.objects.entity.npc.Skin;
import me.trqhxrd.grapesrpg.api.objects.entity.npc.talking.NPCLine;
import me.trqhxrd.grapesrpg.api.objects.entity.npc.talking.NPCMessage;
import me.trqhxrd.grapesrpg.game.config.NPCLocationsConfig;
import org.bukkit.Location;

import java.util.UUID;

public class IntroductionNPC extends GrapesStoryNPC {

    public IntroductionNPC(Location spawn) {
        super("&eJoe", spawn, new Skin(UUID.randomUUID(), "ewogICJ0aW1lc3RhbXAiIDogMTYxNzg5ODY3ODk2NSwKICAicHJvZmlsZUlkIiA6ICJhZTY5NGM1NjBjNzE0ODdkOGI1Y2M5ZjdjYzgwZjY2ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJUcnFoeHJkIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2ViMWYwOWU1YzlmYmY0NzM4ZGI4MzRlNGU3NTNjZGUwNDBiNGQwYjMyMWU2OGJiOGI2NzQwMjIyY2RlZTllNGMiCiAgICB9CiAgfQp9", "PCD8S3Mz4pOInJ9Fo83GXlDFRv4aDblGLEFoKlRE+8umfsjg6w92K6FaYT3tePJqkX0i6mAX/6lT5YsD0nUXZRLfwJt77Ju21mHiEx+B1qnX88q8L2YiiGGIYlvzyRxGurOcitVgT+l5mWyIOfF7Jgiywtsb7AVdIBPnDOubAIamSLHaIn4UGUextg/2aN9cJjtyk6C7Oi7GHYyRVo/F1vaRy7a2qtwC4lj+5IM2ohLiyJ9ACl//oQ+MFaubQsKMipvx+kmbI4hB3gJKAgdMTK14r2wg1gQ+A7xVE9XSNwRtpDnsgw6696m9QmscnFxwMKeqJojE7J3ELNjQ4ScgrJUmNmZLa8KpZHv4QfMSQnh1wTWvMbBgPEPr+atcmRM7rRn6sgermK0/1G7qJ7sYe+Kb6z4ekiFIzfYHYvH7jdmm96HaNGDVgmIx+8/NFiQJzy8yBfEViWE5+X0iMusTykvOzyEwXikujQz8Xox74Vonz1CSWqfzzbZpI1SQMHwIoRlj3AiqDSTgMPlA2cE0CEBMwmcUHGPplj8Tper3fcAGiz2AVDMoFIpb+2IevNrg51tsv9R/6FQrRKq4jxzk8uCYdS2UnVNgub1nFFfccJxvkan3ULU6v1I3MJM24/kA3wWgjhUBDRrqKoOANyiGQH2mKXrnxwMoyEcgeM/A6eQ="));
        this.getMessageEngine().addMessage(new NPCMessage(1, new NPCLine("&aTest Line 1", 1)));
        this.getMessageEngine().addMessage(new NPCMessage(2, new NPCLine("&aTest Line 2", 1)));
    }

    public IntroductionNPC(String locationKey) {
        this(NPCLocationsConfig.getLocation(locationKey));
    }
}
