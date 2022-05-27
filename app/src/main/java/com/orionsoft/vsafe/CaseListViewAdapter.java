package com.orionsoft.vsafe;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.orionsoft.vsafe.model.Case;

import java.util.List;

public class CaseListViewAdapter extends ArrayAdapter<Case> {

    // The case details list that will be displayed
    private List<Case> caseDetailsList;

    // The context object
    private Context mCtx;

    // Here we are getting the case details and context
    // So while creating the object of this adapter class we need to give case details and context
    public CaseListViewAdapter(List<Case> caseDetailsList, Context mCtx) {
        super(mCtx, R.layout.case_list_items, caseDetailsList);
        this.caseDetailsList = caseDetailsList;
        this.mCtx = mCtx;
    }

    // This method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Getting the LayoutInflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        // Creating a view with our XML layout
        View listViewItem = inflater.inflate(R.layout.case_list_items, null, true);

        // Getting text views
        TextView txtCaseID = listViewItem.findViewById(R.id.txtCaseID);
        TextView txtCaseStatus = listViewItem.findViewById(R.id.txtCaseStatus);
        TextView txtCaseDateTime = listViewItem.findViewById(R.id.txtCaseDateTime);
        TextView txtCaseSituation = listViewItem.findViewById(R.id.txtCaseSituation);
        TextView txtCaseDetails = listViewItem.findViewById(R.id.txtCaseDetails);
        TextView txtCaseLocation = listViewItem.findViewById(R.id.txtCaseLocation);

        // Getting the case for the specified position
        Case aCase = caseDetailsList.get(position);

        // Setting case details to TextViews
        txtCaseID.setText(aCase.getId());
        txtCaseStatus.setText((aCase.getStatus() == 0) ? "Unresolved" : "Resolved");
        txtCaseStatus.setTextColor((aCase.getStatus() == 0) ? Color.parseColor("#FF0000") : Color.parseColor("#00FF00"));
        txtCaseDateTime.setText(aCase.getDateTime());
        txtCaseSituation.setText(aCase.getSituation());
        txtCaseDetails.setText(aCase.getDetails());
        txtCaseLocation.setText(aCase.getLocation());

        //returning the listitem
        return listViewItem;
    }
}
