/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arsdigita.cms.contenttypes.ui;

import com.arsdigita.bebop.FormProcessException;
import com.arsdigita.bebop.Table;
import com.arsdigita.bebop.event.TableActionEvent;
import com.arsdigita.bebop.event.TableActionListener;
import com.arsdigita.cms.ItemSelectionModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class SciProjectContactsTable
    extends Table
    implements TableActionListener {

    private static final Logger LOGGER = LogManager
        .getLogger(SciProjectContactsTable.class);

    private final static String TABLE_COL_EDIT = "table_col_edit";
    private final static String TABLE_COL_EDIT_ASSOC = "table_col_edit_assoc";
    private final static String TABLE_COL_DEL = "table_col_del";
    private final static String TABLE_COL_UP = "table_col_up";
    private final static String TABLE_COL_DOWN = "table_col_down";

    private final ItemSelectionModel itemModel;
    private final SciProjectContactsStep editStep;

    public SciProjectContactsTable(final ItemSelectionModel itemModel,
                                   final SciProjectContactsStep editStep) {

        super();
        
        this.itemModel = itemModel;
        this.editStep = editStep;
        
        throw new UnsupportedOperationException("ToDo");

    }

    @Override
    public void cellSelected(final TableActionEvent event) throws FormProcessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void headSelected(final TableActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
