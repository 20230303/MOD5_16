package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

/**
 *   "LOLWeaponsMod:TiYaMaTe": {
 *     "NAME": "提亚马特",
 *     "FLAVOR": "巨刃所及，血肉如潮汐般退散。",
 *     "DESCRIPTIONS": [
 *       "攻击单个目标时，对其他敌人造成25%伤害"
 *     ]
 *   }
 */
public class TiYaMaTe extends CustomRelic {
    public static final String ID = ModHelper.makePath("TiYaMaTe");//修改这里 TODO
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/TiYaMaTe.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/TiYaMaTe.png";
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;//类型：商店
    private static final LandingSound LANDING_SOUND = LandingSound.MAGICAL;//声音：魔法
    public TiYaMaTe() {//注意修改这里 TODO
        //super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
        // 如果你需要轮廓图，取消注释下面一行并注释上面一行，不需要就删除
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new TiYaMaTe();//修改这里 TODO
    }
    // 遗物效果，可在此处添加代码 TODO
    private static final int _DamagePercent = 25;
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
        this.addToBot(new SFXAction("ATTACK_HEAVY"));//播放音效
        this.addToBot(new VFXAction(new CleaveEffect()));//特效

        int damageToOthers = Math.round(card.damage * _DamagePercent / 100f);
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDying && !m.isEscaping && m != action.target) {
                this.addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damageToOthers, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
            }
        }
        flash();
        }
    }
}

