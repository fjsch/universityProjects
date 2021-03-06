package com.example.mediabs;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.HttpException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.ConnectTimeoutException;
import logic.Checklist;
import logic.Patient;
import logic.StatisticData;
import logic.User;
import logic.Ward;
import logic.TimerData;
import restclient.RestUtils;
import com.example.mediabs.R;

import connection.TimerDataSource;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;

/**
 * 
 * @author smh
 * 
 *         This Class starts onClick the whole sync process this includes in
 *         order of appearance
 * 
 *         GET and Update Wards GET and Update Users GET and Update Antibiotics
 *         GET and Update InfectionDiagnosises GET and Update InterventionTypes
 *         Gather StatisticData on marked as treated Patients and remove them as
 *         well from the App Generate TimerData for testing purposes POST
 *         TimerData on Grails Website POST gathered StatisticData on Grails
 *         Website
 * 
 *         * Data for GET import is served on ../object/export sites as JSON
 *         Data Data for POST export is send to ../objectImport/index as XML
 *         Data
 * 
 */

public class SynchronizationActivity extends Activity {

	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_synchronization);
		// Init Update Class
		final Update u = (Update) this.getApplication();

		Button yesButton = (Button) findViewById(R.id.yesButton);
		Button noButton = (Button) findViewById(R.id.noButton);

		TextView introTextView = (TextView) findViewById(R.id.introTextView);
		introTextView
				.setText("Bei erfolgreicher Synchronisation werden alle mit H�kchen markierten"
						+ "Patienten und Timer Zeiten �bertragen und von der App entfernt."
						+ "Checklistenauswahl, Stations- und Userdaten werden auf die App �bertragen.");

		// ButtonListener
		yesButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent toHomeScreen = new
				// Intent(getApplicationContext(),HomeActivity.class);

				// INIT REST
				RestUtils ru = new RestUtils();
				String credentials = u.getCurrentUserName() + ":"
						+ u.getCurrentPassword();

				// init go on flag
				// if there is no exception go to next try block
				boolean noException = true;

				// init Alert Message
				String alertMessage = "";

				// GET WARD List
				ArrayList<Ward> wardList;
				try {
					System.out.println("GET_WARD_LIST");
					wardList = ru.getWardList(credentials);
					u.setWardList(wardList);
					u.storeWards(getApplicationContext());
					System.out.println(wardList);
				} catch (ConnectTimeoutException e) {
					System.out.println("ConnectTimeoutException");
					System.out.println(e);
					alertMessage = "Synchronisationsfehler!\n" + e;
					noException = false;
				} catch (ClientProtocolException e) {
					System.out.println("ClientProtocolException");
					System.out.println(e);
					alertMessage = "Synchronisationsfehler!\n" + e;
					noException = false;
				} catch (IOException e) {
					System.out.println("IOException");
					System.out.println(e);
					alertMessage = "Synchronisationsfehler!\n" + e;
					noException = false;
				} catch (HttpException e) {
					System.out.println("HttpException");
					System.out.println(e);
					alertMessage = "Synchronisationsfehler!\n" + e;
					noException = false;
				}

				// GET USER List
				if (noException) {
					ArrayList<User> userList;
					try {
						System.out.println("GET_USER_LIST");
						userList = ru.getUserList(credentials);
						u.setUserList(userList);
						u.storeUsers(getApplicationContext());
						System.out.println(userList);
					} catch (ConnectTimeoutException e) {
						System.out.println("ConnectTimeoutException");
						System.out.println(e);
						alertMessage = "Synchronisationsfehler!\n" + e;
						noException = false;
					} catch (ClientProtocolException e) {
						System.out.println("ClientProtocolException");
						System.out.println(e);
						alertMessage = "Synchronisationsfehler!\n" + e;
						noException = false;
					} catch (IOException e) {
						System.out.println("IOException");
						System.out.println(e);
						alertMessage = "Synchronisationsfehler!\n" + e;
						noException = false;
					} catch (HttpException e) {
						System.out.println("HttpException");
						System.out.println(e);
						alertMessage = "Synchronisationsfehler!\n" + e;
						noException = false;
					}
				}

				// GET Antibiotic List
				if (noException) {
					ArrayList<String> antibioticList;
					try {
						System.out.println("GET_ANTIBIOTIC_LIST");
						antibioticList = ru.getAntibioticList(credentials);
						u.setAllAntibiotics(antibioticList);
						u.storeAllAntibioticTitles(getApplicationContext());
						System.out.println(antibioticList);
					} catch (ConnectTimeoutException e) {
						System.out.println("ConnectTimeoutException");
						System.out.println(e);
						alertMessage = "Synchronisationsfehler!\n" + e;
						noException = false;
					} catch (ClientProtocolException e) {
						System.out.println("ClientProtocolException");
						System.out.println(e);
						alertMessage = "Synchronisationsfehler!\n" + e;
						noException = false;
					} catch (IOException e) {
						System.out.println("IOException");
						System.out.println(e);
						alertMessage = "Synchronisationsfehler!\n" + e;
						noException = false;
					} catch (HttpException e) {
						System.out.println("HttpException");
						System.out.println(e);
						alertMessage = "Synchronisationsfehler!\n" + e;
						noException = false;
					}
				}

				// GET InfectionDiagnosis List
				if (noException) {
					ArrayList<String> infectionDiagnosisList;
					try {
						System.out.println("GET_INFECTIONDIAGNOSIS_LIST");
						infectionDiagnosisList = ru
								.getInfectionDiagnosisList(credentials);
						System.out.println(infectionDiagnosisList);
						u.setAllDiagnosis(infectionDiagnosisList);
						u.storeAllDiagnosis(getApplicationContext());

					} catch (ConnectTimeoutException e1) {
						System.out.println("ConnectTimeoutException");
						System.out.println(e1);
						alertMessage = "Synchronisationsfehler!\n" + e1;
						noException = false;
					} catch (ClientProtocolException e1) {
						System.out.println("ClientProtocolException");
						System.out.println(e1);
						alertMessage = "Synchronisationsfehler!\n" + e1;
						noException = false;
					} catch (IOException e1) {
						System.out.println("IOException");
						System.out.println(e1);
						alertMessage = "Synchronisationsfehler!\n" + e1;
						noException = false;
					} catch (HttpException e1) {
						System.out.println("HttpException");
						System.out.println(e1);
						alertMessage = "Synchronisationsfehler!\n" + e1;
						noException = false;
					}
				}

				// GET InterventionType List
				if (noException) {
					ArrayList<String> interventionTypeList;
					try {
						System.out.println("GET_INTERVENTIONTYPE_LIST");
						interventionTypeList = ru
								.getInterventionTypeList(credentials);
						u.setAllInterventions(interventionTypeList);
						u.storeAllInterventionTypeTitles(getApplicationContext());
						System.out.println(interventionTypeList);
					} catch (ConnectTimeoutException e1) {
						System.out.println("ConnectTimeoutException");
						System.out.println(e1);
						alertMessage = "Synchronisationsfehler!\n" + e1;
						noException = false;
					} catch (ClientProtocolException e1) {
						System.out.println("ClientProtocolException");
						System.out.println(e1);
						alertMessage = "Synchronisationsfehler!\n" + e1;
						noException = false;
					} catch (IOException e1) {
						System.out.println("IOException");
						System.out.println(e1);
						alertMessage = "Synchronisationsfehler!\n" + e1;
						noException = false;
					} catch (HttpException e1) {
						System.out.println("HttpException");
						System.out.println(e1);
						alertMessage = "Synchronisationsfehler!\n" + e1;
						noException = false;
					}

					// Init Data
					u.initData(getApplicationContext());

				}

				// Gather and POST Timerdata
				if (noException) {
					// gather TimerData
					ArrayList<TimerData> timerDataList = u
							.loadTimerData(getApplicationContext());
					System.out.println("GATHER_TIMERDATA " + timerDataList);
					TimerDataSource timerDatabase = new TimerDataSource(
							getApplicationContext());

					// remove Timerdata from database
					timerDatabase.open();
					timerDatabase.clearTable();
					timerDatabase.close();

					// POST Timerdata
					try {
						System.out.println("POST_TIMERDATA_LIST");
						ru.post(credentials, "timerdata", timerDataList);
						System.out.println(timerDataList);
					} catch (ClientProtocolException e) {
						System.out.println("ClientProtocolException");
						System.out.println(e);
						alertMessage = "Synchronisationsfehler!\n" + e;
						noException = false;
					} catch (IOException e) {
						System.out.println("IOException");
						System.out.println(e);
						alertMessage = "Synchronisationsfehler!\n" + e;
						noException = false;
					} catch (HttpException e) {
						System.out.println("HttpException");
						System.out.println(e);
						alertMessage = "Synchronisationsfehler!\n" + e;
						noException = false;
					}
				}

				// Gather and POST StatisticData
				if (noException) {

					ArrayList<StatisticData> statisticDataList = new ArrayList<StatisticData>();
					ArrayList<Patient> deletePatients = new ArrayList<Patient>();

					// Gather statisticData of marked as treated Patients only
					for (Ward w : u.getWardList()) {
						for (Patient p : w.getPatientList()) {
							if (p.isAbsCareGivingState()) {
								// find Checklist of this patient
								for (Checklist cl : u.getChecklistList()) {
									if (cl.getOwner()
											.equals(p.getDescription())) {
										deletePatients.add(p);
										System.out.println("GATHER_PATIENT "
												+ p.getDescription());
										statisticDataList.add(cl
												.Checklist2StatisticData(u
														.getWardList()));
									}
								}
							}
						}

						// Remove patient from ward
						for (Patient p : deletePatients) {
							w.removePatient(p);
						}
					}

					// Save updated Patient List
					u.storePatients(getApplicationContext());
					u.initData(getApplicationContext());

					try {
						System.out.println("POST_STATISTICDATA_LIST");
						ru.post(credentials, "statisticdata", statisticDataList);
						System.out.println(statisticDataList);
					} catch (ClientProtocolException e) {
						System.out.println("ClientProtocolException");
						System.out.println(e);
						alertMessage = "Synchronisationsfehler!\n" + e;
						noException = false;
					} catch (IOException e) {
						System.out.println("IOException");
						System.out.println(e);
						alertMessage = "Synchronisationsfehler!\n" + e;
						noException = false;
					} catch (HttpException e) {
						System.out.println("HttpException");
						System.out.println(e);
						alertMessage = "Synchronisationsfehler!\n" + e;
						noException = false;
					}
				}

				if (noException) {
					System.out.println("SYNC_SUCCESS");
					alertMessage = "Synchronisation erfolgreich!";
				}

				// set up alert dialog
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// set dialog message
				alertDialogBuilder
						.setMessage(alertMessage)
						.setCancelable(false)
						.setNegativeButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				// show Alert Dialog with Exception or Success Message
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});

		// ButtonListener
		noButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent toHomeScreen = new Intent(getApplicationContext(),
						HomeActivity.class);
				startActivity(toHomeScreen);
			}
		});

	}

}
