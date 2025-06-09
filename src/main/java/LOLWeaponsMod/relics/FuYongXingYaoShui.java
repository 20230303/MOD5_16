package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;

/**
 *   "LOLWeaponsMod:FuYongXingYaoShui": {
 *     "NAME": "复用型药水",
 *     "FLAVOR": "瓶中的月光永远满盈，饮下即获新生。",
 *     "DESCRIPTIONS": ["战斗结束后，若药水栏为空，则获得一瓶药水"]
 *   },
 */
public class FuYongXingYaoShui extends CustomRelic {
    public static final String ID = ModHelper.makePath("FuYongXingYaoShui");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/FuYongXingYaoShui.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/FuYongXingYaoShui.png";
    private static final RelicTier RELIC_TIER = RelicTier.COMMON;//类型：稀有
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;//音效：魔法
    public FuYongXingYaoShui() {
        //super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new FuYongXingYaoShui();
    }
    // 遗物效果，可在此处添加代码 TODO
    public void atBattleStart() {
        if (AbstractDungeon.player.potions.stream().allMatch((p) -> {
            return p instanceof PotionSlot;
        })) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addRandomPotion();
        }

    }

    private void addRandomPotion() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
        } else if (AbstractDungeon.player.hasRelic("Sozu")) {
            AbstractDungeon.player.getRelic("Sozu").flash();
        } else {
            AbstractPotion potionToObtain = AbstractDungeon.returnRandomPotion();
            AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(potionToObtain));
        }

    }

}
