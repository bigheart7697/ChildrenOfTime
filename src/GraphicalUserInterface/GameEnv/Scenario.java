package GraphicalUserInterface.GameEnv;

import abilities.*;
import battleMechanics.Battlefield;
import itemMGMT.Consumable;
import itemMGMT.Equipment;
import itemMGMT.ImmediateEffect;
import itemMGMT.Item;
import player.op.Player;
import units.Fighter;
import units.Hero;
import units.Supporter;
import units.Unit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Scenario {

    private Map map;
    private int rows, columns;
    private Image BGTile, key, story, battle, shop, ability, finalBoss;
    private Image[] Obs, Doors, items, heroes, storyImages;
    private String begin, end;

    private ArrayList<Item> Shop;

    private Player player;
    private Battlefield battlefield;

    private void imageLoader() {
        try {
            Obs = new Image[14];
            for (int i = 0; i < 14; i++) Obs[i] = ImageIO.read(new File("GameEnvGraphics/Obs" + (i + 1) + ".png"));

            Doors = new Image[2];
            for (int i = 0; i < 2; i++) Doors[i] = ImageIO.read(new File("GameEnvGraphics/Door" + i + ".png"));

            items = new Image[9];
            for (int i = 0; i < 9; i++) items[i] = ImageIO.read(new File("ShopGraphics/" + (i + 1) + ".png"));

            heroes = new Image[4];
            for (int i = 0; i < 4; i++) heroes[i] = ImageIO.read(new File("HeroGraphics/" + (i + 1) + ".png"));

            storyImages = new Image[6];
            for (int i = 0; i < 6; i++) storyImages[i] = ImageIO.read(new File("StoryGraphics/" + (i + 1) + ".jpg"));

            BGTile = ImageIO.read(new File("GameEnvGraphics/BGTile.jpg"));
            key = ImageIO.read(new File("GameEnvGraphics/key.png"));
            battle = ImageIO.read(new File("GameEnvGraphics/battle.png"));
            story = ImageIO.read(new File("GameEnvGraphics/story.png"));
            shop = ImageIO.read(new File("GameEnvGraphics/shop.png"));
            ability = ImageIO.read(new File("GameEnvGraphics/ability.png"));
            finalBoss = ImageIO.read(new File("GameEnvGraphics/finalBoss.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Scenario() {

        imageLoader();

        //Default scenario

        //Player
        player = new Player("Endless Collection", 40, 15);
        battlefield = new Battlefield();
        {
            Hero Eley = new Fighter("Eley");
            Eley.setHeroImage(heroes[0]);
            Hero Chrome = new Fighter("Chrome");
            Chrome.setHeroImage(heroes[1]);
            Hero Meryl = new Supporter("Meryl");
            Meryl.setHeroImage(heroes[2]);
            Hero Bolti = new Supporter("Bolti");
            Bolti.setHeroImage(heroes[3]);

             //Add description to the heroes:

            Eley.setDescription("Eley: " +
                    "Class: Fighter " +
                    "Ability 3: Overpowered attack " +
                    "Attacks an enemy with N times power for 2 energy points and 50 magic points " +
                    "Upgrade 1: N=1.2 for 2 xp points, needs Fight training upgrade 1 " +
                    "Upgrade 2: N=1.4 for 4 xp points, needs Fight training upgrade 2 " +
                    "Upgrade 3: N=1.6 for 6 xp points, needs Fight training upgrade 3 " +
                    "Ability 4: Swirling attack " +
                    "While attacking, non-targeted enemies also take P percent of its damage " +
                    "Upgrade 1: P=10 for 2 xp points, needs Work out upgrade 1 " +
                    "Upgrade 2: P=20 for 3 xp points " +
                    "Upgrade 3: P=30 for 4 xp points ");

            Chrome.setDescription("Chrome: " +
                    "Class: Fighter " +
                    "Ability 3: Sacrifice " +
                    "Damages all the enemies with 3H power at the cost of H of his own   health," +
                    " needs 3 energy points, 60 magic points and has a 1 turn cooldown " +
                    "Upgrade 1: H=40 for 2 xp points, needs Work out upgrade 1 " +
                    "Upgrade 2: H=50 for 3 xp points, needs Work out upgrade 2 " +
                    "Upgrade 3: H=60 for 4 xp points, needs Work out upgrade 3 " +
                    "Ability 4: Critical strike " +
                    "Has a permanent P percent chance of doing an attack with double power (does not affect other abilities) " +
                    "Upgrade 1: P=20 for 2 xp points, needs Fight training upgrade 1 " +
                    "Upgrade 2: P=30 for 3 xp points " +
                    "Upgrade 3: P=40 for 4 xp points ");

            Meryl.setDescription("Class: Supporter " +
                    "Ability 3: Elixir " +
                    "Refills H points of her own health or an ally’s, for 2 energy points and 60 magic points " +
                    "Upgrade 1: H=100 for 2 xp points and takes 1 turn to cool down " +
                    "Upgrade 2: H=150 for 3 xp points, takes 1 turn to cool down and needs Magic lessons upgrade 1 " +
                    "Upgrade 3: H=150 for 5 xp points, cools down instantly and needs Magic lessons upgrade 2 " +
                    "Ability 4: Caretaker " +
                    "Gives 1 energy point to an ally for 30 magic points (this ep does not last until the end of  " +
                    "the battle and is only usable during the current turn) " +
                    "Upgrade 1: takes 2 energy points and has a 1 turn cooldown for 2 xp points, needs Quick as a bunny upgrade 1 " +
                    "Upgrade 2: takes 2 energy points and cools down instantly for 3 xp points, needs Quick as a bunny upgrade 2 " +
                    "Upgrade 3 takes 1 energy point and cools down instantly for 5 xp points, needs Quick as a bunny upgrade 3 ");

            Bolti.setDescription("Bolti: " +
                    "Class: Supporter " +
                    "Ability 3: Boost " +
                    "Gives X bonus attack power to himself or an ally, which lasts till the end of the battle, for  " +
                    "2 energy points and 50 magic points (this bonus attack power can stack up) " +
                    "Upgrade 1: A=20 for 2 xp points and takes 1 turn to cool down " +
                    "Upgrade 2: A=30 for 3 xp points and takes 1 turn to cool down " +
                    "Upgrade 3: A=30 for 5 xp points and cools down instantly " +
                    "Ability 4: Mana beam " +
                    "Gives M magic points to himself or an ally for 1 energy point and 50 magic points " +
                    "Upgrade 1: M=50 for 2 xp points and takes 1 turn to cool down, needs magic lessons upgrade 1 " +
                    "Upgrade 2: M=80 for 3 xp points and takes 1 turn to cool down, needs magic lessons upgrade 2 " +
                    "Upgrade 3: M=80 for 4 xp points and cools down instantly, needs magic lessons upgrade 3 ");


            //Add specific abilities for heroes here

            Attacker OverpoweredAttack = new Attacker("Overpowered attack", 0, 2, 46, 50, 222, 0, 246, 0, false ,Eley);
            OverpoweredAttack.setDescription("Overpowered attack " +
                    "Attacks an enemy with N times power for 2 energy points and 50 magic points " +
                    "Upgrade 1: N=1.2 for 2 xp points, needs Fight training upgrade 1 " +
                    "Upgrade 2: N=1.4 for 4 xp points, needs Fight training upgrade 2 " +
                    "Upgrade 3: N=1.6 for 6 xp points, needs Fight training upgrade 3 " +
                    "Success message: “Eley just did an overpowered attack on “ + (target) + “ with “ + (damage done) + “ damage” ");
            Ability[] OverpoweredAttacksRequiredAbility = new Ability[3];
            for(int cnt = 0; cnt < 3; cnt++)
                OverpoweredAttacksRequiredAbility[cnt] = new SelfBoost("Fight training", cnt + 1, 0, 0, "", 0);
            OverpoweredAttack.setRequiredAbility(OverpoweredAttacksRequiredAbility);

            Attacker Sacrifice = new Attacker("Sacrifice", 0, 2, 34, 60, 333, 111, 0, 456, false, Chrome);
            Sacrifice.setDescription("Sacrifice " +
                    "Damages all the enemies with 3H power at the cost of H of his own health, needs 3 energy points, 60 magic points and has a 1 turn cooldown " +
                    "Upgrade 1: H=40 for 2 xp points, needs Work out 1 " +
                    "Upgrade 2: H=50 for 3 xp points, needs Work out 2 " +
                    "Upgrade 3: H=60 for 4 xp points, needs Work out 3 " +
                    "Success message: “Chrome just sacrificed himself to damage all his enemies with “ + (damage done) + “ power“");
            Ability[] SacrificesRequiredAbility = new Ability[3];
            for(int cnt = 0; cnt < 3; cnt++)
                SacrificesRequiredAbility[cnt] = new SelfBoost("Work out", cnt + 1, 0, 0, "", 0);
            Sacrifice.setRequiredAbility(SacrificesRequiredAbility);

            AttackModifier SwirlingAttack = new AttackModifier("Swirling attack", 0, 2, 34, 1, 123, 0, Eley, battlefield);
            SwirlingAttack.setDescription("Swirling attack " +
                    "While attacking, non-targeted enemies also take P percent of its damage " +
                    "Upgrade 1: P=10 for 2 xp points, needs Work out upgrade 1 " +
                    "Upgrade 2: P=20 for 3 xp points " +
                    "Upgrade 3: P=30 for 4 xp points");
            Ability[] SwirlingAttacksRequiredAbility = new Ability[3];
            SwirlingAttacksRequiredAbility[0] = new SelfBoost("Work out", 1, 0, 0, "", 0);
            SwirlingAttacksRequiredAbility[1] = new SelfBoost("", 0, 0, 0, "", 0);
            SwirlingAttacksRequiredAbility[2] = new SelfBoost("", 0, 0, 0, "", 0);
            SwirlingAttack.setRequiredAbility(SwirlingAttacksRequiredAbility);

            AttackModifier CriticalStrike = new AttackModifier("Critical strike", 0, 2, 34, 2, 0, 234, Chrome, battlefield);
            CriticalStrike.setDescription("Critical strike " +
                    "Has a permanent P percent chance of doing an attack with double power (does not affect other abilities) " +
                    "Upgrade 1: P=20 for 2 xp points, needs Fight training upgrade 1 " +
                    "Upgrade 2: P=30 for 3 xp points " +
                    "Upgrade 3: P=40 for 4 xp points ");
            Ability[] CriticalStrikesRequiredAbility = new Ability[3];
            CriticalStrikesRequiredAbility[0] = new SelfBoost("Fight training", 1, 0, 0, "", 0);
            CriticalStrikesRequiredAbility[1] = new SelfBoost("", 0, 0, 0, "", 0);
            CriticalStrikesRequiredAbility[2] = new SelfBoost("", 0, 0, 0, "", 0);
            CriticalStrike.setRequiredAbility(CriticalStrikesRequiredAbility);

            Restorer Elixir = new Restorer("Elixir", 0, 2, 35, 60, 222, 110, "health point", 101515, Meryl);
            Elixir.setDescription("Elixir " +
                    "Refills H points of her own health or an ally’s, for 2 energy points and 60 magic points " +
                    "Upgrade 1: H=100 for 2 xp points and takes 1 turn to cool down " +
                    "Upgrade 2: H=150 for 3 xp points, takes 1 turn to cool down and needs Magic lessons upgrade 1 " +
                    "Upgrade 3: H=150 for 5 xp points, cools down instantly and needs Magic lessons upgrade 2 " +
                    "Success message: “Meryl just healed “ + (target) + “ with “ + (healing amount) + “ health points” ");
            Ability[] ElixirsRequiredAbility = new Ability[3];
            ElixirsRequiredAbility[0] = new SelfBoost("", 0, 0, 0, "", 0);
            ElixirsRequiredAbility[1] = new SelfBoost("Magic lessons", 1, 0, 0, "", 0);
            ElixirsRequiredAbility[2] = new SelfBoost("Magic lessons", 1, 0, 0, "", 0);
            Elixir.setRequiredAbility(ElixirsRequiredAbility);

            Restorer Caretaker = new Restorer("Caretaker", 0, 2, 35, 30, 221, 100, "energy point", 111, Meryl);
            Caretaker.setDescription("Caretaker " +
                    "Gives 1 energy point to an ally for 30 magic points (this ep does not last until the end of the battle and is only usable during the current turn) " +
                    "Upgrade 1: takes 2 energy points and has a 1 turn cooldown for 2 xp points, needs Quick as a bunny upgrade 1 " +
                    "Upgrade 2: takes 2 energy points and cools down instantly for 3 xp points, needs Quick as a bunny upgrade 2 " +
                    "Upgrade 3 takes 1 energy point and cools down instantly for 5 xp points, needs Quick as a bunny upgrade 3 " +
                    "Success message: “Meryl just gave “ + (target) + “ 1 energy point” ");
            Ability[] CaretakersRequiredAbility = new Ability[3];
            for(int cnt = 0; cnt < 3; cnt++)
                CaretakersRequiredAbility[cnt] = new SelfBoost("Quick as a bunny", cnt + 1, 0, 0, "", 0);
            Caretaker.setRequiredAbility(CaretakersRequiredAbility);

            Restorer Boost = new Restorer("Boost", 0, 2, 35, 50, 222, 110, "attack power", 233, Bolti);
            Boost.setDescription("Boost " +
                    "Gives X bonus attack power to himself or an ally, which lasts till the end of the battle, for 2 energy points and 50 magic points (this bonus attack power can stack up) " +
                    "Upgrade 1: A=20 for 2 xp points and takes 1 turn to cool down " +
                    "Upgrade 2: A=30 for 3 xp points and takes 1 turn to cool down " +
                    "Upgrade 3: A=30 for 5 xp points and cools down instantly " +
                    "Success message: “Bolti just boosted “ + (target) + “ with “ + (A) + “ power” ");
            Ability[] BoostsRequiredAbility = new Ability[3];
            for(int cnt = 0; cnt < 3; cnt++)
                BoostsRequiredAbility[cnt] = new SelfBoost("", 0, 0, 0, "", 0);
            Boost.setRequiredAbility(BoostsRequiredAbility);

            Restorer ManaBeam = new Restorer("Mana beam", 0, 2, 34, 50, 111, 110, "magic point", 588, Bolti);
            ManaBeam.setDescription("Mana beam " +
                    "Gives M magic points to himself or an ally for 1 energy point and 50 magic points " +
                    "Upgrade 1: M=50 for 2 xp points and takes 1 turn to cool down, needs magic lessons upgrade 1 " +
                    "Upgrade 2: M=80 for 3 xp points and takes 1 turn to cool down, needs magic lessons upgrade 2 " +
                    "Upgrade 3: M=80 for 4 xp points and cools down instantly, needs magic lessons upgrade 3 " +
                    "Success message: “Bolti just helped “ + (target) + “ with “ + (M) + “ magic points” ");
            Ability[] ManaBeamsRequiredAbility = new Ability[3];
            for(int cnt = 0; cnt < 3; cnt++)
                ManaBeamsRequiredAbility[cnt] = new SelfBoost("Magic lessons", cnt + 1, 0, 0, "", 0);
            ManaBeam.setRequiredAbility(ManaBeamsRequiredAbility);

            //Adding abilities to the heroes:
            Eley.addAbility(OverpoweredAttack);
            Eley.addAbility(SwirlingAttack);
            Chrome.addAbility(Sacrifice);
            Chrome.addAbility(CriticalStrike);
            Meryl.addAbility(Elixir);
            Meryl.addAbility(Caretaker);
            Bolti.addAbility(Boost);
            Bolti.addAbility(ManaBeam);

            ArrayList<Unit> heroesArr = new ArrayList<>();
            heroesArr.add(Eley);
            heroesArr.add(Chrome);
            heroesArr.add(Meryl);
            heroesArr.add(Bolti);

            battlefield.addUnits(heroesArr);

            player.addHero(Eley);
            player.addHero(Chrome);
            player.addHero(Meryl);
            player.addHero(Bolti);
        }

        //Stories
        String[] stories = new String[6];
        begin = stories[0] = "You’ve entered the castle, it takes a while for your eyes to get used to the darkness" +
                " but the horrifying halo of your enemies is vaguely visible. Angel’s unsettling" +
                " presence and the growling of thugs tell you that your first battle is about to begin!";
        stories[1] = "As you wander into the hall you realize the surrounding doors can lead your destiny to "
                + "something far worse than you expected. You now what’s anticipating you behind the only " +
                " open door but there’s no other choice.";
        stories[2] = "The door behind you is shut with a thunderous sound and you progress into the next hall while" +
                " holding the first key that you’ve found, hoping to seek the second one.";
        stories[3] = "Running with the second key in your hand, you unlock  the door  back to  the first hall and "
                + "use the first key  to burst into  your most terrifying nightmares.";
        stories[4] = "You feel hopeless and exhausted as you stalk to the final door.  What’s behind that door " +
                "makes your hearts  pound and your spines  shake with fear,  but you  came here  to do  one" +
                "thing  and backing down is not an option.";
        end  = stories[5] = "The collector falls down on his knees, he’s strained and desperate but still tries to" +
                "drag himself toward Epoch. He knows his era has come to an end. The ancient time machine" +
                "calls you to end the disorder and bring unity under its glorious wings, now it’s your" +
                "turn to be the MASTERS OF TIME!";

        //BattlePhrases
        String[] battlePhrases = new String[6];
        battlePhrases[0] = " ";
        battlePhrases[1] = " ";
        battlePhrases[2] = " ";
        battlePhrases[3] = " ";
        battlePhrases[4] = " ";
        battlePhrases[5] = " ";



        //Shop and items added below
        Shop = new ArrayList<>();

        //List of items created and added to shop here

        ImmediateEffect Toughen = new ImmediateEffect("Toughen", "HP", 20, "“Toughen”: +20 maximum health"); Toughen.setImage(items[0]);
        ImmediateEffect Guide = new ImmediateEffect("Guide", "MP", 20, "“Guide”: +20 maximum magic"); Guide.setImage(items[1]);
        ImmediateEffect Defy = new ImmediateEffect("Defy", "att", 8, "“Defy”: +8 attack power"); Defy.setImage(items[2]);

        Equipment Sword = new Equipment("Sword", 25, 1, "att", 80, "“Sword”: +80 attack power, costs 25 dollars"); Sword.setImage(items[3]);
        Equipment EnergyBoots = new Equipment("Energy Boots", 20, 1, "EP", 1, "“Energy Boots”: +1 energy point, costs 20 dollars"); EnergyBoots.setImage(items[4]);
        Equipment Armor = new Equipment("Armor", 25, 1, "HP", 200, "“Armor”: +200 maximum health, costs 25 dollars"); Armor.setImage(items[5]);
        Equipment MagicStick = new Equipment("Magic Stick", 28, 1, "MP", 150, "“Magic stick”: +150 maximum magic, costs 28 dollars"); MagicStick.setImage(items[6]);

        Consumable HealthPotion = new Consumable("Health Potion", "HP", 100, "“Health potion”: +100 health points for the user or one of his/her allies, costs 15 dollars"); HealthPotion.setImage(items[7]);
        Consumable MagicPotion = new Consumable("Magic Potion", "MP", 50, "“Magic potion”: +50 magic points for the user or one of his/her allies, costs 15 dollars"); MagicPotion.setImage(items[8]);
        HealthPotion.setSuccessMessage1("just healed");
        HealthPotion.setSuccessMessage2("with 100 health points");
        MagicPotion.setSuccessMessage1("just offered");
        MagicPotion.setSuccessMessage2("50 magic points");

        Shop.add(Toughen);
        Shop.add(Guide);
        Shop.add(Defy);
        Shop.add(Sword);
        Shop.add(EnergyBoots);
        Shop.add(Armor);
        Shop.add(MagicStick);
        Shop.add(HealthPotion);
        Shop.add(MagicPotion);



        map = new Map(rows = 16, columns = 16);

        //Tiles

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                map.setTile(new Tile(BGTile, i + 1, j + 1, true, Tile.Type.tile), i + 1, j + 1);
            }
        }

        //Around the Map
        map.setTile(new Tile(Obs[6], 1, 1, false, Tile.Type.obstacle), 1, 1);
        map.setTile(new Tile(Obs[7], 16, 1, false, Tile.Type.obstacle), 16, 1);
        map.setTile(new Tile(Obs[8], 16, 16, false, Tile.Type.obstacle), 16, 16);
        map.setTile(new Tile(Obs[9], 1, 16, false, Tile.Type.obstacle), 1, 16);

        for (int i = 2; i < 16; i++) {
            map.setTile(new Tile(Obs[0], 1, i, false, Tile.Type.obstacle), 1, i);
            map.setTile(new Tile(Obs[0], 16, i, false, Tile.Type.obstacle), 16, i);
            map.setTile(new Tile(Obs[1], i, 1, false, Tile.Type.obstacle), i, 1);
            map.setTile(new Tile(Obs[1], i, 16, false, Tile.Type.obstacle), i, 16);
        }

        //In Map Obstacles
        map.setTile(new Tile(Obs[11], 5, 16, false, Tile.Type.obstacle), 5, 16);
        map.setTile(new Tile(Obs[0], 5, 15, false, Tile.Type.obstacle), 5, 15);
        map.setTile(new Tile(Obs[0], 5, 14, false, Tile.Type.obstacle), 5, 14);
        map.setTile(new Tile(Obs[7], 5, 13, false, Tile.Type.obstacle), 5, 13);
        map.setTile(new Tile(Obs[1], 4, 13, false, Tile.Type.obstacle), 4, 13);
        map.setTile(new Tile(Obs[9], 3, 13, false, Tile.Type.obstacle), 3, 13);
        map.setTile(new Tile(Obs[0], 3, 12, false, Tile.Type.obstacle), 3, 12);
        map.setTile(new Tile(Obs[0], 3, 11, false, Tile.Type.obstacle), 3, 11);
        map.setTile(new Tile(Obs[2], 3, 10, false, Tile.Type.obstacle), 3, 10);

        map.setTile(new Tile(Obs[12], 1, 4, false, Tile.Type.obstacle), 1, 4);
        map.setTile(new Tile(Obs[3], 2, 4, false, Tile.Type.obstacle), 2, 4);
        map.setTile(new Tile(Obs[5], 4, 4, false, Tile.Type.obstacle), 4, 4);
        map.setTile(new Tile(Obs[1], 5, 4, false, Tile.Type.obstacle), 5, 4);
        map.setTile(new Tile(Obs[1], 6, 4, false, Tile.Type.obstacle), 6, 4);
        map.setTile(new Tile(Obs[11], 7, 4, false, Tile.Type.obstacle), 7, 4);
        map.setTile(new Tile(Obs[2], 7, 3, false, Tile.Type.obstacle), 7, 3);
        map.setTile(new Tile(Obs[1], 8, 4, false, Tile.Type.obstacle), 8, 4);
        map.setTile(new Tile(Obs[1], 9, 4, false, Tile.Type.obstacle), 9, 4);
        map.setTile(new Tile(Obs[3], 10, 4, false, Tile.Type.obstacle), 10, 4);

        map.setTile(new Tile(Obs[3], 7, 6, false, Tile.Type.obstacle), 7, 6);
        map.setTile(new Tile(Obs[1], 6, 6, false, Tile.Type.obstacle), 6, 6);
        map.setTile(new Tile(Obs[6], 5, 6, false, Tile.Type.obstacle), 5, 6);
        map.setTile(new Tile(Obs[0], 5, 7, false, Tile.Type.obstacle), 5, 7);
        map.setTile(new Tile(Obs[0], 5, 8, false, Tile.Type.obstacle), 5, 8);
        map.setTile(new Tile(Obs[9], 5, 9, false, Tile.Type.obstacle), 5, 9);
        map.setTile(new Tile(Obs[1], 6, 9, false, Tile.Type.obstacle), 6, 9);
        map.setTile(new Tile(Obs[1], 7, 9, false, Tile.Type.obstacle), 7, 9);
        map.setTile(new Tile(Obs[1], 8, 9, false, Tile.Type.obstacle), 8, 9);
        map.setTile(new Tile(Obs[1], 9, 9, false, Tile.Type.obstacle), 9, 9);
        map.setTile(new Tile(Obs[10], 10, 9, false, Tile.Type.obstacle), 10, 9);
        map.setTile(new Tile(Obs[0], 10, 8, false, Tile.Type.obstacle), 10, 8);
        map.setTile(new Tile(Obs[0], 10, 7, false, Tile.Type.obstacle), 10, 7);
        map.setTile(new Tile(Obs[2], 10, 6, false, Tile.Type.obstacle), 10, 6);
        map.setTile(new Tile(Obs[9], 10, 10, false, Tile.Type.obstacle), 10, 10);
        map.setTile(new Tile(Obs[1], 11, 10, false, Tile.Type.obstacle), 11, 10);
        map.setTile(new Tile(Obs[3], 12, 10, false, Tile.Type.obstacle), 12, 10);

        map.setTile(new Tile(Obs[11], 10, 16, false, Tile.Type.obstacle), 10, 16);
        map.setTile(new Tile(Obs[0], 10, 15, false, Tile.Type.obstacle), 10, 15);
        map.setTile(new Tile(Obs[0], 10, 14, false, Tile.Type.obstacle), 10, 14);
        map.setTile(new Tile(Obs[10], 10, 13, false, Tile.Type.obstacle), 10, 13);
        map.setTile(new Tile(Obs[6], 10, 12, false, Tile.Type.obstacle), 10, 12);
        map.setTile(new Tile(Obs[1], 9, 13, false, Tile.Type.obstacle), 9, 13);
        map.setTile(new Tile(Obs[5], 8, 13, false, Tile.Type.obstacle), 8, 13);
        map.setTile(new Tile(Obs[1], 11, 12, false, Tile.Type.obstacle), 11, 12);
        map.setTile(new Tile(Obs[3], 12, 12, false, Tile.Type.obstacle), 12, 12);

        map.setTile(new Tile(Obs[13], 14, 1, false, Tile.Type.obstacle), 14, 1);
        map.setTile(new Tile(Obs[0], 14, 2, false, Tile.Type.obstacle), 14, 2);
        map.setTile(new Tile(Obs[0], 14, 3, false, Tile.Type.obstacle), 14, 3);
        map.setTile(new Tile(Obs[0], 14, 4, false, Tile.Type.obstacle), 14, 4);
        map.setTile(new Tile(Obs[0], 14, 5, false, Tile.Type.obstacle), 14, 5);
        map.setTile(new Tile(Obs[4], 14, 6, false, Tile.Type.obstacle), 14, 6);
        map.setTile(new Tile(Obs[0], 16, 6, false, Tile.Type.obstacle), 16, 6);

        //Events

        //Doors
        GameEvent door1 = new GameEvent(Doors[0], 5, 5, false, GameEvent.Type.doorLockedLeft, map.getTile(5, 5));
        GameEvent door2 = new GameEvent(Doors[0], 3, 4, false, GameEvent.Type.doorLockedUp, map.getTile(3, 4));
        GameEvent door3 = new GameEvent(Doors[0], 15, 6, false, GameEvent.Type.doorLockedDown, map.getTile(15, 6));
        GameEvent door4 = new GameEvent(Doors[1], 10, 5, false, GameEvent.Type.doorUnlockedLeft, map.getTile(10, 5));
        GameEvent door5 = new GameEvent(Doors[1], 10, 11, false, GameEvent.Type.doorUnlockedLeft, map.getTile(10, 11));
        map.setEvent(door1, 5, 5);
        map.setEvent(door2, 3, 4);
        map.setEvent(door3, 15, 6);
        map.setEvent(door4, 10, 5);
        map.setEvent(door5, 10, 11);

        //Keys
        GameEvent key1 = new GameEvent(key, 12, 15, true, GameEvent.Type.key, map.getTile(12, 15), door1);
        GameEvent key2 = new GameEvent(key, 2, 2, true, GameEvent.Type.key, map.getTile(2, 2), door2);
        GameEvent key3 = new GameEvent(key, 6, 8, true, GameEvent.Type.key, map.getTile(6, 8), door3);
        map.setEvent(key1, 12, 15);
        map.setEvent(key2, 2, 2);
        map.setEvent(key3, 6, 8);

        //Shops
        GameEvent shop1 = new GameEvent(shop, 4, 14, false, GameEvent.Type.shop, map.getTile(4, 14));
        GameEvent shop2 = new GameEvent(shop, 4, 12, false, GameEvent.Type.shop, map.getTile(4, 12));
        GameEvent shop3 = new GameEvent(shop, 12, 9, false, GameEvent.Type.shop, map.getTile(12, 9));
        map.setEvent(shop1, 4, 14);
        map.setEvent(shop2, 4, 12);
        map.setEvent(shop3, 12, 9);

        //Story spots
        GameEvent story1 = new GameEvent(story, 2, 10, true, GameEvent.Type.story, map.getTile(2, 10), stories[1], storyImages[1]);
        GameEvent story2 = new GameEvent(story, 15, 15, true, GameEvent.Type.story, map.getTile(15, 15), stories[2], storyImages[2]);
        GameEvent story3 = new GameEvent(story, 9, 8, true, GameEvent.Type.story, map.getTile(9, 8), stories[3], storyImages[3]);
        GameEvent story4 = new GameEvent(story, 15, 4, true, GameEvent.Type.story, map.getTile(15, 4), stories[4], storyImages[4]);
        map.setEvent(story1, 2, 10);
        map.setEvent(story2, 15, 15);
        map.setEvent(story3, 9, 8);
        map.setEvent(story4, 15, 4);

        //Ability Upgrade points
        GameEvent abilityPoint1 = new GameEvent(ability, 4, 15, false, GameEvent.Type.ability, map.getTile(4, 15));
        GameEvent abilityPoint2 = new GameEvent(ability, 9, 10, false, GameEvent.Type.ability, map.getTile(9, 10));
        GameEvent abilityPoint3 = new GameEvent(ability, 13, 2, false, GameEvent.Type.ability, map.getTile(13, 2));
        map.setEvent(abilityPoint1, 4, 15);
        map.setEvent(abilityPoint2, 9, 10);
        map.setEvent(abilityPoint3, 13, 2);

        //Battles
        GameEvent battle1 = new GameEvent(battle, 2, 12, false, GameEvent.Type.battle, map.getTile(2, 12));
        GameEvent battle2 = new GameEvent(battle, 13, 11, false, GameEvent.Type.battle, map.getTile(13, 11));
        GameEvent battle3 = new GameEvent(battle, 7, 2, false, GameEvent.Type.battle, map.getTile(7, 2));
        GameEvent battle4 = new GameEvent(battle, 7, 5, false, GameEvent.Type.battle, map.getTile(7, 5));
        GameEvent battleOptional = new GameEvent(battle, 9, 14, false, GameEvent.Type.battle, map.getTile(9, 14));
        map.setEvent(battle1, 2, 12);
        map.setEvent(battle2, 13, 11);
        map.setEvent(battle3, 7, 2);
        map.setEvent(battle4, 7, 5);
        map.setEvent(battleOptional, 9, 14);

        //Final Boss
        GameEvent finalBattle = new GameEvent(finalBoss, 15, 2, false, GameEvent.Type.battle, map.getTile(15, 2));
        map.setEvent(finalBattle, 15, 2);

    }

    public Scenario(int num) {

        imageLoader();

    }


    Player getPlayer() { return player; }

    Map getMap() {
        return map;
    }
    int getRows() { return rows; }
    int getColumns() { return columns; }

    Image getUnlockedDoorImg() { return Doors[1]; }

    ArrayList<Item> getShop() { return Shop; }

    String getBeginStory() { return begin; }
    Image getBeginImage() { return storyImages[0]; }
    String geEndStory() { return end; }
    Image getEndImage() { return storyImages[5]; }

}

