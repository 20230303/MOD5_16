package LOLWeaponsMod.relics;

import LOLWeaponsMod.helpers.ModHelper;
import basemod.abstracts.CustomRelic;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.MadnessAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

/**遗物：纳沃利迅刃
 * 效果：每回合，每使用4张牌，随机一张手牌耗能减为0
 * */
public class NaWoLiXunRen extends CustomRelic {
    public static final String ID = ModHelper.makePath("NaWoLiXunRen");
    public static final String IMG_PATH = "PEACH_LOLWeaponsModResources/img/relics/NaWoLiXunRen.png";
    public static final String OUTLINE_PATH = "PEACH_LOLWeaponsModResources/img/relics/NaWoLiXunRen.png";
    private static final AbstractRelic.RelicTier RELIC_TIER = AbstractRelic.RelicTier.BOSS;//类型:BOSS
    private static final AbstractRelic.LandingSound LANDING_SOUND = AbstractRelic.LandingSound.MAGICAL;//声音:魔法
    public NaWoLiXunRen() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }
    //获取描述,但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public AbstractRelic makeCopy() {
        return new NaWoLiXunRen();
    }
    // 遗物效果，可在此处添加代码 TODO

//    public void onEquip(){//拾取时触发
//        CardCrawlGame.sound.play("GOLD_GAIN");//播放音效
//        AbstractDungeon.player.gainGold(33);//获得33金币
//        AbstractDungeon.player.increaseMaxHp(3, true);//获得3点最大生命
//    }
private static final int _Draw = 1;
    private static final int _Count = 4;
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (true) {
            ++this.counter;
            if (this.counter == _Count) {
                flash();
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));//将遗物放置在卡牌上方
                addToBot(new MadnessAction());//触发疯狂状态
                this.counter = 0;
            }
        } else {
            this.counter = 0;
        }
    }
    public void atBattleStart() {
        this.counter = 0;
    }


}
