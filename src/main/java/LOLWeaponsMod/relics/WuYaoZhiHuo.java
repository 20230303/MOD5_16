package LOLWeaponsMod.relics;


import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.commons.codec.binary.StringUtils;

/**遗物：巫妖之祸
 * 效果：每次战斗中的第一次攻击额外造成 #b 8 点伤害。在每个回合开始时获得 #b 4 点 #y 活力。
 * */
public class WuYaoZhiHuo extends CustomRelic {
    public static final String ID = ModHelper.makePath("WuYaoZhiHuo");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/WuYaoZhiHuo.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/WuYaoZhiHuo.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;//类型：罕见
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;//声音：魔法
    public WuYaoZhiHuo() {//注意修改这里 TODO
        //super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        //修改这里 TODO
        return new WuYaoZhiHuo();
    }
    // 遗物效果，可在此处添加代码 TODO

//    public void onEquip() {//这段代码的作用是，在获得遗物时，将Akabeko遗物移除
//        if (AbstractDungeon.player.hasRelic("Akabeko")) {
//            AbstractDungeon.player.loseRelic("Akabeko");
//        }
//    }

    public void atBattleStart() {//这段代码的作用是，在战斗开始时，将遗物效果应用
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, 6), 6));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public void atTurnStart() {//这段代码的作用是，在回合开始时，将遗物效果应用
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, 4), 4));
    }

//    public void obtain() {//这段代码的作用是，在获得遗物时，将遗物效果应用.具体来说，如果玩家已经有Akabeko遗物，则生成本遗物，否则生成Akabeko遗物
//        if (AbstractDungeon.player.hasRelic("Akabeko")) {
//            for(int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
//                if (StringUtils.equals(((AbstractRelic)AbstractDungeon.player.relics.get(i)).relicId, "Akabeko")) {
//                    this.instantObtain(AbstractDungeon.player, i, true);
//                    break;
//                }
//            }
//        } else {
//            super.obtain();
//        }
//    }

//    public boolean canSpawn() {//这段 代码是用来判断是否有Akabeko遗物的，如果有，则不能生成本遗物
//        return AbstractDungeon.player.hasRelic("Akabeko");
//    }
}
