package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

/**
 *   "LOLWeaponsMod:DuoMiNiKeLingZhuDeZhiYi": {
 *     "NAME": "多米尼克领主的致意",
 *     "FLAVOR": "致意？不，这是对败者的最后通牒。",
 *     "DESCRIPTIONS": [
 *       "巨人杀手：所有BOSS的生命值减少25%"
 *     ]
 *   },
 */
public class DuoMiNiKeLingZhuDeZhiYi extends CustomRelic {
    public static final String ID = ModHelper.makePath("DuoMiNiKeLingZhuDeZhiYi");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/DuoMiNiKeLingZhuDeZhiYi.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/DuoMiNiKeLingZhuDeZhiYi.png";
    private static final RelicTier RELIC_TIER = RelicTier.SHOP;//类型:BOSS
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;//声音:魔法
    public DuoMiNiKeLingZhuDeZhiYi() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new DuoMiNiKeLingZhuDeZhiYi();
    }
    // 遗物效果，可在此处添加代码 TODO
    private float MODIFIER_AMT = 0.25F;
    @Override
    public void atBattleStart() {
        if (AbstractDungeon.dungeonMapScreen.map.atBoss) {//如果是BOSS战
            this.flash();
            Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();//获取所有BOSS

            while(var1.hasNext()) {//
                AbstractMonster m = (AbstractMonster)var1.next();
                if (m.currentHealth > (int)((float)m.maxHealth * (1.0F - this.MODIFIER_AMT))) {
                    m.currentHealth = (int)((float)m.maxHealth * (1.0F - this.MODIFIER_AMT));
                    m.healthBarUpdatedEvent();
                }
            }

            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }

    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.actNum <= 1;//只在第1关出现
    }


}
