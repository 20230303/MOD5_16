package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static com.megacrit.cardcrawl.cards.DamageInfo.*;
import static com.megacrit.cardcrawl.cards.DamageInfo.DamageType.*;

/**
 * "LOLWeaponsMod:WuZhongHenYi": {
 *     "NAME": "无终恨意",
 *     "FLAVOR": "死亡仅是蛰伏，恨意永无终焉。",
 *     "DESCRIPTIONS": [
 *       "回合结束时，对随机敌人造成等于格挡的伤害"
 *     ]
 *   }
 */
public class WuZhongHenYi extends CustomRelic {
    public static final String ID = ModHelper.makePath("WuZhongHenYi");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/WuZhongHenYi.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/WuZhongHenYi.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.RARE;//类型:稀有
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.HEAVY;//声音:重击
    public WuZhongHenYi() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new WuZhongHenYi();
    }
    // 遗物效果，可在此处添加代码 TODO
    // 用THORNS伤害造成伤害,非攻击伤害。如果用NORMAL,会吃到力量加成
    @Override
    public void onPlayerEndTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            int damage = AbstractDungeon.player.currentBlock;
            this.addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }
}
