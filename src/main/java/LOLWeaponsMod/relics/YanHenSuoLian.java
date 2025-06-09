package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

/**
 "LOLWeaponsMod:YanHenSuoLian": {
 "NAME": "厌恨锁链",
 "FLAVOR": "她曾发誓要用她的一生来毁灭他......",
 "DESCRIPTIONS": [
 "腐化之心失去死亡律动与坚不可摧"
 ]
 }
 */
public class YanHenSuoLian extends CustomRelic {
    public static final String ID = ModHelper.makePath("YanHenSuoLian");//修改这里 TODO
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/YanHenSuoLian.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/YanHenSuoLian.png";
    private static final RelicTier RELIC_TIER = RelicTier.SHOP;//类型：商店
    private static final LandingSound LANDING_SOUND = LandingSound.CLINK;//声音：点击
    public YanHenSuoLian() {//注意修改这里 TODO
        //super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new YanHenSuoLian();//修改这里 TODO
    }
    // 遗物效果，可在此处添加代码 TODO
    public void atBattleStart() {
        if (AbstractDungeon.dungeonMapScreen.map.atBoss) {
            this.flash();
            Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            while(var1.hasNext()) {
                AbstractMonster m = (AbstractMonster)var1.next();
                if (m.hasPower("BeatOfDeath")) {
                    this.addToTop(new RemoveSpecificPowerAction(m, m, "BeatOfDeath"));
                }

                if (m.hasPower("Invincible")) {
                    this.addToTop(new RemoveSpecificPowerAction(m, m, "Invincible"));
                }
            }

            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }

    }

}
