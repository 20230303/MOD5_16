package LOLWeaponsMod.relics;

import LOLWeaponsMod.cards.Reconstruction;
import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

/**
 * "LOLWeaponsMod:FuWenDiaoKeZhe": {
 *     "NAME": "符文雕刻者",
 *     "FLAVOR": "指间流淌的世界符文，正在重写现实法则。",
 *     "DESCRIPTIONS": ["你每使用一张牌，对所有敌人施加1层印记"]
 *   },
 */
public class FuWenDiaoKeZhe extends CustomRelic {
    public static final String ID = ModHelper.makePath("FuWenDiaoKeZhe");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/FuWenDiaoKeZhe.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/FuWenDiaoKeZhe.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.BOSS;//类型:BOSS
    //所有类型:COMMON,UNCOMMON,RARE,SPECIAL,BOSS,SHOP,DEPRECATED,STARTER
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:魔法


    public FuWenDiaoKeZhe() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new FuWenDiaoKeZhe();
    }
    // 遗物效果，可在此处添加代码 TODO
    // 遗物效果，可在此处添加代码 TODO
    // 拾起该遗物时，将一张Reconstruction卡牌加入卡组
    public void onEquip() {
        //for(int i = 1; i <= 2; ++i) {// 随机生成2张卡牌,如果要1张，则注释掉这行
            float x = MathUtils.random(0.1F, 0.9F) * (float)Settings.WIDTH;
            float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;
            AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new Reconstruction(), x, y));
        //}

    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            AbstractPlayer p = AbstractDungeon.player;
            this.addToBot(new RelicAboveCreatureAction(p, this));
            // 选择一个随机敌人
            com.megacrit.cardcrawl.monsters.AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            if (randomMonster != null) {
                this.addToBot(new ApplyPowerAction(randomMonster, p, new MarkPower(randomMonster, 1), 1));
            }
        }
    }

}
