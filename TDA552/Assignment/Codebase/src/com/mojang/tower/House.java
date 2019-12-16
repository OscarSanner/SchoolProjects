package com.mojang.tower;

import java.awt.Color;
import java.awt.Graphics2D;

public class House extends Entity {
    private static final int POPULATION_PER_RESIDENCE = 10;
    private static final int WARRIORS_PER_BARRACKS = 5;
    public static final int FOOD_PER_PEON = 5;
    public static final int WOOD_PER_WARRIOR = 5;

    private HouseType type;
    private int buildTime;
    private int buildDuration = 32 * 6;
    private int animFrame = 0;
    private int maxHp = 256;
    private int hp = maxHp;

    //TODO: A house has a housetype, one could argue that this is an attempt to use the strategy pattern.
    //TODO: However, nothing is ever delegated to the housetype, and the housetype is one class. Further investigation is required. It's simply used to go through if statements. A true Strategy pattern could be used!
    //TODO: Delegation is actually used to get images.
    public House(double x, double y, HouseType type) {
        super(x, y, type.radius);
        this.type = type;
    }

    //Fights with a monster. Doesn't effect the monsters HP. In fact the monster is never used.
    //Poor implementation as it shouldn't override. Once again. Liskov.
    public void fight(Monster monster) {
        if (hp <= 0) return;
        if (--hp <= 0) {
            die();
        }
    }

    //Kills the house. Fine
    public void die() {
        Sounds.play(new Sound.Destroy());
        if (type == HouseType.RESIDENCE) {
            island.populationCap -= POPULATION_PER_RESIDENCE;
        }
        if (type == HouseType.BARRACKS) {
            island.warriorPopulationCap -= WARRIORS_PER_BARRACKS;
        }
        alive = false;
    }

    //Poor naming? Obviously it's the build that's complete, but could perhaps be more obvious.
    public void complete() {
        hp = maxHp;
        buildTime = buildDuration;
    }

    //Here we see some delegation to the type. "type" could very easily be any of a number of strategies.
    public boolean acceptsResource(int resourceId) {
        return buildTime >= buildDuration && type.acceptResource == resourceId;
    }

    //Here we see some delegation to the type. "type" could very easily be any of a number of strategies.
    public boolean submitResource(int resourceId) {
        if (buildTime >= buildDuration && type.acceptResource == resourceId) {
            Sounds.play(new Sound.Gather());
            puff(); //What's puff??? It's smoke. It ads smoke when you submit a resource to the house.
            return true;
        }
        return false;
    }

    //Every house appears to have the same build duration.
    //Self explanatory. Odd ifs, pop cap could be kept by some main model class.
    public boolean build() {
        if (buildTime < buildDuration) {
            buildTime++;
            if (hp < maxHp) hp += 1;
            if (hp > maxHp) hp = maxHp;
            if (buildTime == buildDuration) {
                Sounds.play(new Sound.FinishBuilding());
                if (type == HouseType.RESIDENCE) {
                    island.populationCap += POPULATION_PER_RESIDENCE;
                }
                if (type == HouseType.BARRACKS) {
                    island.warriorPopulationCap += WARRIORS_PER_BARRACKS;
                }
            }
        }
        return buildTime == buildDuration;
    }

    //The ticker ran by update?
    public void tick() {
        animFrame++;
        //If statement checks if the house is in an incomplete STATE.
        if (buildTime < buildDuration) {
            for (int i = 0; i < 2; i++) {
                //Tries twice to fetch a peon to build itself.
                Peon peon = getRandomPeon(100, 80, true);
                //Can an indefinite amount of peons work on house??
                if (peon != null && peon.job == null) {
                    peon.setJob(new Job.Build(this));
                }
            }
            //If in complete STATE: increase hp if hp < maxHp
        } else {
            if (hp < maxHp && random.nextInt(4) == 0) {
                hp++;
            }

            //Difficult to understand game logic. Acquires a peon to work for the house.
            Peon peon = getRandomPeon(50, 50, true);
            if (peon != null && peon.job == null && peon.type == 0) {
                //Creates a new instance of TargetFilter with an overridden accepts method.
                TargetFilter noMobFilter = new TargetFilter() {
                    public boolean accepts(Entity e) {
                        return !(e instanceof Peon || e instanceof Monster);
                    }
                };
                if (type == HouseType.MASON) {
                    peon.setJob(new Job.Gather(Resources.ROCK, this));
                } else if (type == HouseType.WOODCUTTER) {
                    peon.setJob(new Job.Gather(Resources.WOOD, this));
                } else if (type == HouseType.WINDMILL) {
                    peon.setJob(new Job.Gather(Resources.FOOD, this));
                } else if (type == HouseType.PLANTER) {
                    if (getRandomTarget(6, 40, noMobFilter) == null)
                        peon.setJob(new Job.Plant(this, 0));
                } else if (type == HouseType.FARM) {
                    if (getRandomTarget(6, 40, noMobFilter) == null)
                        peon.setJob(new Job.Plant(this, 1));
                }
            }

            if (type == HouseType.GUARDPOST) {
                peon = getRandomPeon(80, 80, true);
                if (peon != null && peon.job == null && (peon.type == 0 && random.nextInt(2) == 0)) {
                    peon.setJob(new Job.Goto(this));
                }
            }

            if (type == HouseType.BARRACKS && island.warriorPopulation < island.warriorPopulationCap && island.resources.wood >= WOOD_PER_WARRIOR) {
                peon = getRandomPeon(80, 80, true);
                if (peon != null && peon.job == null && peon.type == 0) {
                    peon.setJob(new Job.GotoAndConvert(this));
                }
            }

            if (type == HouseType.RESIDENCE && island.population < island.populationCap && island.resources.food >= FOOD_PER_PEON && random.nextInt(20) == 0) {
                double xt = x + (random.nextDouble() * 2 - 1) * 9;
                double yt = y + (random.nextDouble() * 2 - 1) * 9;

                peon = new Peon(xt, yt, 0);
                if (island.isFree(peon.x, peon.y, peon.r)) {
                    puff();
                    island.resources.food -= FOOD_PER_PEON;
                    island.addEntity(peon);
                    Sounds.play(new Sound.Spawn());
                }
            }
        }
    }

    //Unused
    public void destroy() {
        if (type == HouseType.RESIDENCE) {
            island.populationCap -= POPULATION_PER_RESIDENCE;
        }
        if (type == HouseType.BARRACKS) {
            island.warriorPopulationCap -= POPULATION_PER_RESIDENCE;
        }
    }

    //Returns a random Peon. mustBeFree specifies if the peon has to be free from work.
    private Peon getRandomPeon(double r, double s, final boolean mustBeFree) {
        TargetFilter peonFilter = new TargetFilter() {
            public boolean accepts(Entity e) {
                return e.isAlive() && (e instanceof Peon) && (!mustBeFree || ((Peon) e).job == null);
            }       };

        //Target filter specifies what target to fetch.
        Entity e = getRandomTarget(r, s, peonFilter);
        if (e instanceof Peon) {
            Peon peon = (Peon) e;
            return peon;
        }
        return null;
    }

    //TODO: Responsible for rendering itself. Not very good.
    public void render(Graphics2D g, double alpha) {
        int x = (int) (xr - 8);
        int y = -(int) (yr / 2 + 16 - 4);

        if (type == HouseType.GUARDPOST) y -= 2;
        if (type == HouseType.WINDMILL) y -= 1;

        if (buildTime < buildDuration) {
            g.drawImage(bitmaps.houses[0][buildTime * 6 / buildDuration], x, y, null);
        } else {
            g.drawImage(type.getImage(bitmaps), x, y, null);
        }

        if (hp < maxHp) {
            g.setColor(Color.BLACK);
            g.fillRect(x + 4, y - 2, 8, 1);
            g.setColor(Color.RED);
            g.fillRect(x + 4, y - 2, hp * 8 / maxHp, 1);
        }
    }

    //Adds smoke.
    public void puff() {
        island.addEntity(new Puff(x, y));
    }

    //Returns some of the price of the house. Delegation is used. Dies.
    public void sell() {
        island.resources.wood += type.wood * 3 * hp / (maxHp * 4);
        island.resources.rock += type.rock * 3 * hp / (maxHp * 4);
        die();
    }
    //TODO: To summarise, this class is responsible for drawing all the houses, it's also responsible for the common properties of houses
    //TODO: Furthermore, it uses a form of strategy pattern, however it's not complete. Housetypes should be an abstract interface and
    //TODO: the individual houses should have their own properties to reduce code and increase extensibility.
}