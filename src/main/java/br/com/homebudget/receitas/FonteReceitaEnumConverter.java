package br.com.homebudget.receitas;

import br.com.homebudget.despesas.CategoriaDespesaEnum;
import br.com.homebudget.shared.helper.EnumConverter;

public class FonteReceitaEnumConverter extends EnumConverter<FonteReceitaEnum> {
    public FonteReceitaEnumConverter(){
        super(FonteReceitaEnum.class);
    }
}