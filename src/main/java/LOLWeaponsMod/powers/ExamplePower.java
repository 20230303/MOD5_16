package LOLWeaponsMod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
/*
public ApplyPowerAction(
        AbstractCreature target, // 目标
        AbstractCreature source, // 施法者
        AbstractPower powerToApply,// 要应用的能力
        int stackAmount, // 能力层数
        AbstractGameAction.AttackEffect effect// 效果
        )
        {
        this(target, source, powerToApply, stackAmount, false, effect);
 }
        */

public class ExamplePower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = LOLWeaponsMod.helpers.ModHelper.makePath("ExamplePower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // 能力的层数
    private static int postfix = 0;
    public ExamplePower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        //确保每个能力ID不一样，否则会导致冲突
        this.ID = POWER_ID + postfix++;
        //this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;

        // 添加一大一小两张能力图
        String path128 = "PEACH_LOLWeaponsModResources/img/powers/Example84.png";
        String path48 = "PEACH_LOLWeaponsModResources/img/powers/Example32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        //this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        this.description = String.format(DESCRIPTIONS[0], this.amount); // 这样，%d就被替换成能力的层数
    }

    // 被攻击时
    public int onAttacked(DamageInfo info, int damageAmount) {
        // 非荆棘伤害，非生命流失伤害，伤害来源不为空，伤害来源不是能力持有者本身，伤害大于0
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && damageAmount > 0) {
            // 能力闪烁一下
            this.flash();

            // 添加回复action
            this.addToTop(new HealAction(owner, owner, this.amount));
        }

        // 如果该能力不会修改受到伤害的数值，按原样返回即可
        return damageAmount;
    }
}
