import java.io.File; // Pacchetto che dà possibilità di creare il file
import java.io.FileNotFoundException; // Eccezione riguardante il file nel caso non sia trovato
import java.io.IOException; // Eccezione a controllo obbligatorio (errore non gestibile dal programmatore)
import java.lang.IndexOutOfBoundsException; // Eccezione a "controllo npn obbligatorio" (errore programmatore)
import java.lang.NumberFormatException;  // Eccezione riguardante la mancata estrazione di un intero
import java.util.Collections; // Pacchetto che offre dei metodi (statici) applicabili su diversi contenitori 
import java.util.ArrayList; // Pacchetto che richiama le classi che consentono di memorizzare raccolte di oggetti
import java.util.Calendar; // Pacchetto che include classi che permettono di definire la data in maniera istantanea
import javax.swing.*;  // Importa il package swing , ossia le classi per interfacce utenti, e i sottopackage   

/**
 * Rappresenta una biblioteca astratta con un elenco di clienti, libri e noleggi. 
 * 
 * @author Carlone Giuseppe Pio 
 * @author Mambella Alessandra
 * @author Sichetti Dylan
 */

public class Main {

   public static void main(String[] args)  throws FileNotFoundException, IOException, NumberFormatException{
       

                boolean safe=true;
		boolean fatto=false;
		int opt=-1;
		JTextField InsTesto=new JTextField();
		Object[] scelta={"Cosa si desidera fare (digitare il numero corrispondente)?"
                        + "\n\n1) Aggiungere un libro al database"
                        + "\n\n2) Mostrare i libri disponibili"
			+ "\n\n3) Mostrare i noleggi scaduti\n\n4) Cercare un libro,cliente o noleggio"
			+ "\n\n5) Uscire","\n",InsTesto};
		File film=new File("libri.txt");
		File prestiti=new File("prestiti.txt");
                File clienti=new File("clienti.txt");
		Videoteca vid=new Videoteca();
		FileManager manager=new FileManager(vid);
		manager.Lettura(film, clienti,prestiti);
		do
		{
			try
			{
				UIManager.put("OptionPane.cancelButtonText", "Esci");
				UIManager.put("OptionPane.okButtonText", "OK");
				InsTesto.setText("");
				int option=JOptionPane.showConfirmDialog(null, scelta, "Menù",
					JOptionPane.OK_CANCEL_OPTION);
				if(option==JOptionPane.OK_OPTION)
				{
					opt=Integer.parseInt(InsTesto.getText()); 
					if(opt<0 || opt>7)
					{
						JOptionPane.showMessageDialog(null,"Numero Immesso non valido."
							+ " Riprovare:");
					}
					else
					{
						switch(opt)
						{
							case 1: safe=false; AggiungiNuovoFilm(vid);                      break;
							case 2: safe=false; MostraLibri(vid);                             break;
							case 3: safe=false; ControlloScad(vid);                           break;
							case 4: safe=false; Ricerca(vid);                                 break;
                                                        case 5: fatto=Uscita(safe,film,prestiti,clienti,manager,fatto);  break;
						}
					}
				}
				else
				{
                                    fatto=Uscita(safe,film,prestiti,clienti,manager,fatto);
				}
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null,"Selezione Immessa non valida. Riprovare:");
			}
				
		}
		while(!fatto);
	}
	        
    /**
     * Questa funzione aggiunge un nuovo film alla videoteca 
     * @param film Oggetto di tipo Videoteca la quale gestisce un elenco di libri, clienti e noleggi
     * @throws NumberFormatException Eccezione riguardante la mancata estrazione di un intero
     * @throws IOException Eccezione a controllo obbligatorio (errore non gestibile dal programmatore)
     */
        
       public static void AggiungiNuovoFilm(Film film) throws NumberFormatException, IOException
	{
		int opzione;
		JTextField titolo=new JTextField();
		JTextField codice=new JTextField();
		JTextField NomeRegista=new JTextField();
		JTextField CognomeRegista=new JTextField();
		JTextField NazioneRegista=new JTextField();
                JTextField Copie=new JTextField();
		Object[] StatoFilm={"Titolo:",titolo,"Codice ISBN:",codice,"Numero di copie:",Copie,"Nome dell'"
			+ "autore:",NomeRegista,
			"Cognome dell'autore:",CognomeRegista,"Nazione dell'autore:",NazioneRegista};
		boolean fatto=false;
		do
		{
			try
			{
				UIManager.put("OptionPane.cancelButtonText", "Torna al Menù");
				UIManager.put("OptionPane.okButtonText", "Aggiungi");
				opzione=JOptionPane.showConfirmDialog(null,StatoFilm,"Inserire"
				+ " i dati sul libro:",JOptionPane.OK_CANCEL_OPTION);
				UIManager.put("OptionPane.okButtonText", "OK");
				if(opzione==JOptionPane.OK_OPTION)
				{
					if(Integer.parseInt(Copie.getText())<0)
					{
						JOptionPane.showMessageDialog(null, "Il numero delle copie immesse"
							+ " non può essere negativo. Ricontrollare i parametri: ");
					}
					else
					{
						fatto=true;
						Film film1=new Film();
						Regista AL=new Regista();
						film1.setTitolo(titolo.getText());
						film1.setCodice(codice.getText());
						film1.setDisponibilità(Integer.parseInt(Copie.getText()));
						AL.setNome(NomeRegista.getText());
						AL.setCognome(CognomeRegista.getText());
						AL.setNazione(NazioneRegista.getText());
						film1.setRegista(AL);
						vid.AggiungiFilm(film1);
						JOptionPane.showMessageDialog(null,"Il libro è stato"
						+ " aggiunto con successo!");
					}
				}
				else
				{
					fatto=true;
					JOptionPane.showMessageDialog(null, "Immissione annullata!");
				}
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "I dati immessi sono errati o mancanti."
					+ " Riprovare:");
			}
		}
		while(!fatto);
	}
	
    /**
     * Questa funziona esegue il prestito
     * @param lib Oggetto di tipo Videoteca la quale gestisce un elenco di libri, clienti e noleggi
     * @throws NumberFormatException Eccezione riguardante la mancata estrazione di un intero
     */
       
	public static void Prestito(Videoteca lib) throws NumberFormatException
	{
		JTextField NomeCliente=new JTextField();
		JTextField CognomeCliente=new JTextField();
		JTextField codicef=new JTextField();
		JTextField NomeFilm=new JTextField();
		Object[] StatoPrest={"Nome del cliente:",NomeCliente,"Cognome del cliente:",CognomeCliente,
			"Codice fiscale:",codicef,"Titolo del libro da noleggiare:",NomeFilm};
		
		boolean fatto=false;
		do
		{
			try
			{
				UIManager.put("OptionPane.cancelButtonText", "Torna al Menù");
				UIManager.put("OptionPane.okButtonText", "Noleggia");
				int opzione=JOptionPane.showConfirmDialog(null, StatoPrest, "Informazioni per il noleggio",
				JOptionPane.OK_CANCEL_OPTION);
				if(opzione==JOptionPane.OK_OPTION)
				{
					UIManager.put("OptionPane.okButtonText", "OK");
					Cliente cliente1=new Cliente();
					cliente1.setNome(NomeCliente.getText());
					cliente1.setCognome(CognomeCliente.getText());
					cliente1.setCodiceFisc(codicef.getText());
					Film film1=lib.CercaFilmTitolo(NomeFilm.getText());
					if(film1!=null)
					{
						Data DataPrestito=DataOdierna();
						Prestito prestito=new Prestito(film1, cliente1, DataPrestito);
						if(lib.Noleggio(prestito))
						{
							JOptionPane.showMessageDialog(null,"Noleggio effettuato, "
							+"la riconsegna deve avvenire entro il "+prestito.getScadenza().getGiorno()+
							"/"+prestito.getScadenza().getMese()+"/"+prestito.getScadenza().getAnno());
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Il cliente ha 5 "
								+ "noleggi e/o il libro non è disponibile!");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Il libro inserito non è disponibile!");
					}
				}
				else
				{
					UIManager.put("OptionPane.okButtonText", "OK");
					JOptionPane.showMessageDialog(null,"Noleggio non effettuato!");
				}
				fatto=true;
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "I dati immessi sono errati o mancanti."
					+ " Riprovare");
			}
		}
		while(!fatto);
	}
	
 /**
  * Questa funzione esegue una restituzione inserendo le credenziali del cliente
  * @param lib Oggetto di tipo Videoteca la quale gestisce un elenco di libri, clienti e noleggi
  * @throws NumberFormatException Eccezione riguardante la mancata estrazione di un intero 
  */
        
	public static void Restituzione(Videoteca vid) throws NumberFormatException
	{
		JTextField NomeD=new JTextField();
		JTextField CognomeD=new JTextField();
		JTextField CodiceD=new JTextField();
		JTextField TL=new JTextField();
		Object[] StatoRestit={"Credenziali del cliente:\nNome:",NomeD,"Cognome:",CognomeD,"Codice fiscale:",CodiceD,
			"Titolo del libro noleggiato:",TL};
		boolean fatto=false;
		do
		{
			try
			{
				UIManager.put("OptionPane.cancelButtonText", "Torna al Menù");
				UIManager.put("OptionPane.okButtonText", "Restituire");
				int opzione=JOptionPane.showConfirmDialog(null, StatoRestit, "Restituzione libro",
				JOptionPane.OK_CANCEL_OPTION);
				if(opzione==JOptionPane.OK_OPTION)
				{
					UIManager.put("OptionPane.okButtonText", "OK");
					Prestito prestito=vid.RicercaPrestito(TL.getText(), NomeD.getText(), CognomeD.getText(), CodiceD.getText());
				
					if(prestito!=null)
					{
						vid.Restituzione(prestito);
						JOptionPane.showMessageDialog(null,"Restituzione effettuata con successo!");
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Non ci sono noleggi con i parametri"
							+ " inseriti");
					}
				}
				else
				{
					UIManager.put("OptionPane.okButtonText", "OK");
					JOptionPane.showMessageDialog(null, "Restituzione annullata!");
				}
				fatto=true;
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "I dati immessi sono errati o mancanti."
					+ " Riprovare");
			}
		}
		while(!fatto);
	}
/**
 * Questa funzione dà la possibilità di effettuare una ricerca nella libreria su tre rami:
 * 1) Ricerca attraverso i libri;
 * 2) Ricerca attraverso i clienti;
 * 3) Ricerca attraverso i noleggi.
 * @param vid Oggetto di tipo Videoteca la quale gestisce un elenco di libri, clienti e noleggi
 * @throws NumberFormatException Eccezione riguardante la mancata estrazione di un intero
 */
        
	public static void Ricerca(Videoteca vid) throws NumberFormatException
	{
		String[] opzioni = new String[] {"Libri", "Clienti", "Noleggi", "Torna al Menù"};
		int choice=JOptionPane.showOptionDialog(null, "Cosa si desidera ricercare?\n\nLibri, Clienti"
			+ " o Noleggi?", "Ricerca...",
			JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, opzioni, opzioni[0]);
		switch(choice)
		{
			case 0: RicercaLibri(vid); break;
			case 1: RicercaClienti(vid); break;
			case 2: RicercaPerPrestiti(vid); break;
			case 3: break;
		}
		
	}


/**
 * Imposta la data attuale da cui far partire il controllo sulle scadenze
 * @return DataOdierna 
 */
        
	public static Data DataOdierna()
	{
		Calendar data=null;
		data=Calendar.getInstance();
		Data dataodierna=new Data(data.get(Calendar.DATE),data.get(Calendar.MONTH),data.get(Calendar.YEAR));
		return dataodierna;
	}

/**
 * Questa funzione mostra i libri disponibili indicando i loro dati e dando l'opportunità di effettuare il noleggio 
 * @param lib Oggetto di tipo Videoteca la quale gestisce un elenco di libri, clienti e noleggi
 */
        
	public static void MostraLibri(Videoteca vid)
	{
		int i=1;
		String s="";
		Collections.sort(vid.getFilm());
		for(Film FilmLista:vid.getFilm())
		{
			s=s+i+". "+FilmLista.getTitolo()+" - "+FilmLista.getCodice()
			+" - "+FilmLista.getRegista().getNome()+" "+FilmLista.getRegista().getCognome()+
				" - "+"Copie Disponibili: "+FilmLista.getDisponibilità()+"\n";
			i++;
		}
		s=s+"\n";
		s=s+"Si desidera noleggiarne uno?\n";
		UIManager.put("OptionPane.cancelButtonText", "Torna al Menù");
		UIManager.put("OptionPane.okButtonText", "Noleggia");
		int scelta=JOptionPane.showConfirmDialog(null,s,
			"Cosa vuoi fare?",JOptionPane.OK_CANCEL_OPTION);


		switch(scelta)
		{
			case JOptionPane.OK_OPTION:     Prestito(vid); break;
			case JOptionPane.CANCEL_OPTION:            break;
		}
	}

/**
 * Questa funzione analizza le date dei prestiti indicandone le scadenze con la possibilità di mostrare eventuali prestiti scaduti
 * @param lib Oggetto di tipo Videoteca la quale gestisce un elenco di libri, clienti e noleggi
 */
        
	public static void ControlloScad(Videoteca vid)
	{
		int opzione;
		Data data3=DataOdierna();
		ArrayList<Prestito> scaduto=new ArrayList<Prestito>();
		Collections.sort(vid.getPrestiti());
		for(Prestito PrestitoLista:vid.getPrestiti())
		{
			Data data4=PrestitoLista.getScadenza();
			opzione=data4.compareTo(data3);
			if(opzione==-1)
			{
				scaduto.add(PrestitoLista);
			}
		}
		
		if(scaduto.isEmpty())
		{
			JOptionPane.showMessageDialog(null,"Non ci sono noleggi scaduti in memoria!");
		}
		else
		{
			String s="";
			int i=0;
			for(Prestito PrestitoLista:scaduto)
			{
				s=s+i+") "+PrestitoLista.getScadenza()+": "+PrestitoLista.getFilm().getTitolo()+" - "+
					PrestitoLista.getCliente().getNome()+" "+
					PrestitoLista.getCliente().getCognome()+" "+
					PrestitoLista.getCliente().getCodiceFisc()+"\n";
				i++;
			}
			String s1=s+"\nCosa si vuole fare?";
			String[] opzioni={"Applicare una restituzione avvenuta in ritardo",
				"Rimuovere un libro non più restituito", "Tornare al Menù"};
			int scelta=JOptionPane.showOptionDialog(null, s1, "Menù",
			JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, opzioni, opzioni[0]);
			
			switch(scelta)
			{
				case 0: Restituzione(vid); break;
				case 1: Rimuovi(vid,s,scaduto); break;
				case 2: break;
			}
					
		}
		
	}
	
/**
 * Questa funzione esegue la rimozione di un noleggio dalla lista dei prestiti presente nella libreria.
 * @param lib Oggetto di tipo Videoteca la quale gestisce un elenco di libri, clienti e noleggi
 * @param stringa Oggetto di tipo String attraverso il quale viene indicato il testo da inserire.
 * @param exp ArrayList contenente i prestiti da cui effettuare poi la rimozione.
 * @throws NumberFormatException Eccezione riguardante la mancata estrazione di un intero
 */
        
        public static void Rimuovi(Videoteca lib,String stringa,ArrayList<Prestito> exp) throws NumberFormatException
	{
		boolean fatto=false;
		JTextField scelta=new JTextField();
		int opzione;
		stringa=stringa+"\n\n"+ "Digitare il numero corrispondente al noleggio da rimuovere";
		Object[] message={stringa, scelta};
		do
		{
			try
			{
				UIManager.put("OptionPane.cancelButtonText", "Torna al Menù");
				UIManager.put("OptionPane.okButtonText", "OK");
				scelta.setText("");
				opzione=JOptionPane.showConfirmDialog(null,message,"Scelta",JOptionPane.OK_CANCEL_OPTION);
				if(opzione==JOptionPane.OK_OPTION)
				{
					opzione=Integer.parseInt(scelta.getText());
					lib.getPrestiti().remove(exp.get(opzione));
					fatto=true;
					JOptionPane.showMessageDialog(null,"Rimozione effettuata con successo!");
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Operazione annullata!");
					fatto=true;
				}
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Immissione errata/non valida! Riprovare:");
			}
			catch(IndexOutOfBoundsException e)
			{
				JOptionPane.showMessageDialog(null, "Immissione errata/non valida! Riprovare:");
			}
		}
		while(!fatto);
	}

/**
 * Questa funzione esegue la ricerca dei libri attraverso le seguenti sotto-categorie:
 * 1) Ricerca attraverso l'autore;
 * 2) Ricerca attraverso il titolo;
 * Di seguito a seconda della scelta, vengono richiamate le funzioni apposite.
 * @param lib Oggetto di tipo Videoteca la quale gestisce un elenco di libri, clienti e noleggi
 * @throws NumberFormatException Eccezione riguardante la mancata estrazione di un intero
 */
            
	public static void RicercaLibri(Videoteca lib) throws NumberFormatException
	{
		String s="Cosa si vuole fare?\n\nCercare libri in base al:";
		String[] opzioni={"Titolo", "Regista", "Tornare al Menù"};
		int scelta=JOptionPane.showOptionDialog(null, s, "Ricerca",
			JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, opzioni, opzioni[0]);
		switch(scelta)
		{
			case 0: RicercaFilmTitolo(lib);  break;
			case 1: RicercaFilmRegista(lib);  break;
			case 2: break;
		}
		
	}

/**
 * Questa funzione effettua la ricerca dei clienti attraverso l'inserimento delle credenziali verificando anche i prestiti disponibili per il singolo cliente. 
 * @param lib Oggetto di tipo Videoteca la quale gestisce un elenco di libri, clienti e noleggi
 * @throws NumberFormatException Eccezione riguardante la mancata estrazione di un intero
 */
            
	public static void RicercaClienti(Videoteca lib) throws NumberFormatException
	{
		JTextField NomeCl1=new JTextField();
		JTextField CognomeCl1=new JTextField();
		JTextField CodicefCl1=new JTextField();
		Object[] message={"Inserire nome, cognome e codice fiscale del cliente:", " Nome: ", NomeCl1,
			" Cognome:", CognomeCl1, " Codice fiscale: ", CodicefCl1};
                
		boolean fatto=false;
		do
		{
			try
			{
				UIManager.put("OptionPane.cancelButtonText", "Torna al Menù");
				UIManager.put("OptionPane.okButtonText", "Cerca");
				NomeCl1.setText("");
				CognomeCl1.setText("");
				CodicefCl1.setText("");
				int opzione=JOptionPane.showConfirmDialog(null, message,
					"Ricerca clienti", JOptionPane.OK_CANCEL_OPTION);
				if(opzione==JOptionPane.OK_OPTION)
				{
					Cliente cliente1=new Cliente();
					cliente1=lib.CercaCliente(NomeCl1.getText(), CognomeCl1.getText(), CodicefCl1.getText());
					if(cliente1==null)
					{
						JOptionPane.showMessageDialog(null,"Il cliente non è "
							+ "presente nel database!");
						fatto=true;
					}
					else
					{
						fatto=true;
						String s="Nome: "+cliente1.getNome()
						+" "+cliente1.getCognome()+" "+
							"    Noleggi effettuati: "+cliente1.getPrestiti()+"\n\n";
						if(cliente1.getPrestiti()>0)
						{
                                 			ArrayList<Prestito> PrestitiAtt=lib.CercaPrestitoCliente(NomeCl1.getText(),CognomeCl1.getText(), CodicefCl1.getText());
							PrestitiClienteAtt(lib, s, PrestitiAtt);
						}
						else
						{
							JOptionPane.showMessageDialog(null, s+"\n\nIl cliente non"
								+ "ha noleggi all'attivo...");
						}

					}
				}
				else
				{
					UIManager.put("OptionPane.okButtonText", "OK");
					JOptionPane.showMessageDialog(null, "Operazione annullata!");
					fatto=true;
				}
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Immissione non valida. Riprovare: ");
			}
		}
		while(!fatto);
	}
        
/**
 * Questa funzione analizza i prestiti attivi del cliente, valutando anche la possibilità di una restituzione.
 * @param lib Oggetto di tipo Videoteca la quale gestisce un elenco di libri, clienti e noleggi
 * @param s Oggetto di tipo String che indica l'inserimento del testo desiderato con il richiamo delle funzioni.
 * @param PrestitiAtt ArrayList dei prestiti in attivo del cliente.
 * @throws NumberFormatException Eccezione riguardante la mancata estrazione di un intero 
 */
	
	public static void PrestitiClienteAtt(Videoteca lib, String s, ArrayList<Prestito> PrestitiAtt) throws NumberFormatException
	{
		int i=1;
		int opzione=0;
		JTextField scelta=new JTextField();
		for(Prestito PrestitoLista:PrestitiAtt)
		{
			s=s+i+". "+PrestitoLista.getFilm().getTitolo()+
				" - Data noleggio: "+PrestitoLista.getData()+
				"   Data scadenza: "+PrestitoLista.getScadenza()+"\n";
			i++;
		}
		Object[] message1={s,"Si desidera effettuare una restituzione?"
			+ "\n1)Si\n2)No\n"
			,scelta};
		boolean fatto=false;
		do
		{
			try
			{
				UIManager.put("OptionPane.cancelButtonText", "Torna al Menù");
				UIManager.put("OptionPane.okButtonText", "OK");
				scelta.setText("");
				opzione=JOptionPane.showConfirmDialog(null,message1,
					"Scelta",JOptionPane.OK_CANCEL_OPTION);
				if(opzione==JOptionPane.OK_OPTION)
				{
					opzione=Integer.parseInt(scelta.getText());
					if(opzione<1 || opzione>2)
					{
						JOptionPane.showMessageDialog(null,
							"Immissione "
							+ "non valida. Riprovare: ");
					}
					else
					{
						fatto=true;
						switch(opzione)
						{
							case 1: Restituzione(lib); break;
							case 2: break;
						}
					}
				}
				else
				{
					fatto=true;
					JOptionPane.showMessageDialog(null, 
						"Operazione annullata!");
				}
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Immissione errata o non valida. Riprovare:");
			}
			
		}
		while(!fatto);
	}
	
/**
 * Questa funzione ci permette di trovare un noleggio (inserendo il titolo del libro eventualmente noleggiato),restituendoci i dati del noleggio richiesto
 * (nome, cognome, codice fiscale del cliente che ha effettuato il noleggio, data effettiva e data di scadenza del noleggio stesso).
 * @param lib Oggetto di tipo Videoteca la quale gestisce un elenco di libri, clienti e noleggi
 * @throws NumberFormatException Eccezione riguardante la mancata estrazione di un intero
 */
        
	public static void RicercaPerPrestiti(Videoteca lib) throws NumberFormatException
	{
		JTextField TL=new JTextField();
		Object[] message={"Inserire il nome del libro da controllare:", TL};
		boolean fatto=false;
		do
		{
			try
			{
				UIManager.put("OptionPane.cancelButtonText", "Torna al Menù");
				UIManager.put("OptionPane.okButtonText", "Cerca");
				TL.setText("");
				int opzione=JOptionPane.showConfirmDialog(null, message, "Nome del libro",
					JOptionPane.OK_CANCEL_OPTION);
				if(opzione==JOptionPane.OK_OPTION)
				{
					ArrayList<Prestito> prestiti=lib.CercaPrestitoFilm(TL.getText());
					if(prestiti.isEmpty())
					{
						JOptionPane.showMessageDialog(null,
							"Il libro non è nel database o non è noleggiato da nessuno!");
					}
					else
					{
						int i=1;
						int width=40;
						int pad=0;
						String s=prestiti.get(0).getFilm().getTitolo()+
							" è noleggiato dai seguenti clienti: \n";
						String s1,s2;
						for(Prestito PrestitoLista:prestiti)
						{
							s1=PrestitoLista.getCliente().getNome()+" "+PrestitoLista.getCliente().getCognome()+" \nCodice Fiscale: "+PrestitoLista.getCliente().getCodiceFisc();
							pad=width-s1.length()-(1)/(i+2);
							for(int j=0; j<pad; j++)
							{
								s1=s1+" ";
							}
							s= s+i + " Nome: " + s1 +" "+
                                                        "  Data noleggio: "+
                                                        PrestitoLista.getData()+" Data scadenza: "+
                                                        PrestitoLista.getScadenza()+"\n";
							s1=null;
							i++;
						}
						JOptionPane.showMessageDialog(null, s);
					}
					fatto=true;
				}
				else
				{
					UIManager.put("OptionPane.okButtonText", "OK");
					JOptionPane.showMessageDialog(null, "Operazione annullata!");
					fatto=true;
				}
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Opzione immessa errata o mancante. Riprovare:");
			}
		}
		while(!fatto);
	}
	
/**
 * Questa funzione ci permette di trovare un libro (eventualmente presente nella biblioteca) attraverso il titolo del libro stesso,
 * ci restituisce in output i seguenti dati: nome, cognome, nazionalità dell'autore, codice ISBN del libro con la relativa disponibilità.
 * @param lib Oggetto di tipo Videoteca la quale gestisce un elenco di libri, clienti e noleggi
 * @throws NumberFormatException Eccezione riguardante la mancata estrazione di un intero
 */
        
	public static void RicercaFilmTitolo(Videoteca lib) throws NumberFormatException
	{
		JTextField TL=new JTextField();
		Object[] message={"Inserire il titolo del libro:", TL};
		UIManager.put("OptionPane.cancelButtonText", "Torna al Menù");
		UIManager.put("OptionPane.okButtonText", "Cerca");
		TL.setText("");
		int opzione=JOptionPane.showConfirmDialog(null, message,
			"Per Titolo", JOptionPane.OK_CANCEL_OPTION);
		if(opzione==JOptionPane.OK_OPTION)
		{
			Film film1=lib.CercaFilmTitolo(TL.getText());
			if(film1==null)
			{
				UIManager.put("OptionPane.okButtonText", "OK");
				JOptionPane.showMessageDialog(null,
					"Il libro non è presente in biblioteca!");
			}
			else
			{
				UIManager.put("OptionPane.cancelButtonText", "Torna al Menù");
				UIManager.put("OptionPane.yesButtonText", "Noleggia");
				UIManager.put("OptionPane.noButtonText", "Rimuovi dalla biblioteca");
				String s="Titolo: "+film1.getTitolo()
				+"   Regista: " +film1.getRegista().getNome()+" " + film1.getRegista().getCognome()+ " Nazione: "+film1.getRegista().getNazione()+
                                        "  Codice ISBN: " + film1.getCodice() + " Disponibilità: " + film1.getDisponibilità()+"\n";
				s=s+"\nCosa si desidera fare?\n";
				opzione=JOptionPane.showConfirmDialog(null, s, "Menù",
					JOptionPane.YES_NO_CANCEL_OPTION);
				switch(opzione)
				{
					case JOptionPane.YES_OPTION:     
					if(film1.getDisponibilità()==0)
					{
						UIManager.put("OptionPane.okButtonText", "OK");
						JOptionPane.showMessageDialog(null, "Film non disponibile per il noleggio!");
					}          
					else
					{
						Prestito(lib);
					}
					break;
					case JOptionPane.NO_OPTION:      film1.setDisponibilità(0);          break;
					case JOptionPane.CANCEL_OPTION:                              break;
				}

			}
		}
		else
		{
			UIManager.put("OptionPane.okButtonText", "OK");
			JOptionPane.showMessageDialog(null, "Operazione annullata!");
		}
	}
	
/**
 * Questa funzione ci permette di trovare un libro (eventualmente presente nella biblioteca) attraverso l'autore del libro stesso,
 * ci restituisce in output i seguenti dati: il titolo del libro, nome e cognome dell'autore, codice ISBN e la disponibilità del libro stesso.
 * @param lib Oggetto di tipo Videoteca la quale gestisce un elenco di libri, clienti e noleggi 
 */
	
	public static void RicercaFilmRegista(Videoteca lib)
	{
		JTextField NomeRegista=new JTextField();
		JTextField CognomeRegista=new JTextField();
		Object[] message={"Inserire nome e cognome dell'autore del libro:", "Nome:", NomeRegista,
		"Cognome: ", CognomeRegista};
		UIManager.put("OptionPane.cancelButtonText", "Torna al Menù");
		UIManager.put("OptionPane.okButtonText", "Cerca");
		NomeRegista.setText("");
		CognomeRegista.setText("");
		int opzione=JOptionPane.showConfirmDialog(null, message,
			"Per Regista", JOptionPane.OK_CANCEL_OPTION);
		if(opzione==JOptionPane.OK_OPTION)
		{
			ArrayList<Film> libriAtt=new ArrayList<Film>();
			libriAtt=lib.CercaFilmRegista(NomeRegista.getText(), CognomeRegista.getText());
			if(libriAtt.isEmpty())
			{
				JOptionPane.showMessageDialog(null,"Non ci sono libri appartenenti a "
					+ "tali autori!");
			}
			else
			{
				String s="";
				int i=1;
				for(Film FilmLista:libriAtt)
				{
					s=s+i+" - Titolo: "+FilmLista.getTitolo()+
					" - Regista: "+ FilmLista.getRegista().getNome()+" "+FilmLista.getRegista().getCognome()+
					" - Nazione: "+ FilmLista.getRegista().getNazione()+
				        " - Codice ISBN: "+ FilmLista.getCodice()+
				        " - Copie Disponibili: "+ FilmLista.getDisponibilità()+"\n";
					i++;
				}
				s=s+"Si desidera noleggiarne uno?";
				UIManager.put("OptionPane.okButtonText", "Noleggia");
				UIManager.put("OptionPane.cancelButtonText", "Torna al Menù");
				opzione=JOptionPane.showConfirmDialog(null,s,
					"Scelta",JOptionPane.OK_CANCEL_OPTION);
				switch(opzione)
				{
					case JOptionPane.OK_OPTION:     Prestito(lib); break;
					case JOptionPane.CANCEL_OPTION: break;
				}

			}
		}
		else
		{
			UIManager.put("OptionPane.okButtonText", "OK");
			JOptionPane.showMessageDialog(null, "Operazione annullata!");
		}
	}
        
        
/**
 * Funzione che esegue l'uscita.
 * @param safe Variabile booleana che ci permette di salvare lo stato attuale della biblioteca.
 * @param libri File che include l'elenco dei libri
 * @param prestiti File che include l'elenco dei prestiti
 * @param clienti File che include l'elenco dei clienti
 * @param manager Oggetto di tipo FileManager che gestisce l'operazione di lettura e scrittura su file.
 * @param fatto Variabile booleana che indica se l'uscita è stata eseguita o meno.
 * @return fatto
 * @throws FileNotFoundException Eccezione riguardante il file nel caso non sia trovato
 * @throws IOException Eccezione a controllo obbligatorio (errore non gestibile dal programmatore)
 */

	
	public static boolean Uscita(boolean safe, File libri, File prestiti, File clienti,FileManager manager, boolean fatto)
		throws FileNotFoundException, IOException
	{
		if(!safe)
		{
			UIManager.put("OptionPane.yesButtonText", "Uscire senza Salvare");
			UIManager.put("OptionPane.noButtonText", "Salva ed Esci");
			UIManager.put("OptionPane.cancelButtonText", "Torna al Menù");
			int scelta=JOptionPane.showConfirmDialog(null, "Uscire senza salvare?", "Uscita", 
				JOptionPane.YES_NO_CANCEL_OPTION);
			UIManager.put("OptionPane.okButtonText", "OK");
			
			switch(scelta)
			{
				case JOptionPane.YES_OPTION:    fatto=true; safe=true;                                break;
				case JOptionPane.NO_OPTION:     fatto=true; safe=true; manager.Scrittura(libri, prestiti,clienti); break;
				case JOptionPane.CANCEL_OPTION:                                                      break;
			}
		}
		else 
		{
			fatto=true;
		}
		
		return fatto;
	}
	    
}