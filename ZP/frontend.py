import tkinter as tk
from tkinter import ttk, filedialog, messagebox
from backend.core import Interface, PublicRingToken, PrivateRingToken, Crypted, Signature
from typing import List


def refreshRings():
    global publicRing, privateRing, keyRingSampledData, keyRingSampledDataPrivateRing, keyRingSampledDataPublicRing
    publicRing = Interface.getPublicRing()
    privateRing = Interface.getPrivateRing()
    keyRingSampledData = []
    keyRingSampledDataPrivateRing = []
    keyRingSampledDataPublicRing = []
    for ring in privateRing:
        item = Interface.formSampleStringFromRings(ring, asymmetricAlgorithms)
        keyRingSampledDataPrivateRing.append(item)
        keyRingSampledData.append(item)
    for ring in publicRing:
        item = Interface.formSampleStringFromRings(ring, asymmetricAlgorithms)
        keyRingSampledDataPublicRing.append(item)
        keyRingSampledData.append(item)
    tmpList = [i for i in keyRingSampledData if keyRingSampledData.count(i) > 1]
    keyRingSampledData = []
    for temp in tmpList:
        if temp not in keyRingSampledData:
            keyRingSampledData.append(temp)
    global dropdownDel
    if dropdownDel is not None:
        dropdownDel['values'] = keyRingSampledData
    if dropdownPrivateRingSendMessage is not None:
        dropdownPrivateRingSendMessage['values'] = [ring for ring in keyRingSampledDataPrivateRing
                                                    if ring.find("ElGamal 1024") == -1 and
                                                    ring.find("ElGamal 2048") == -1
                                                    ]
    if dropdownPublicRingSendMessage is not None:
        dropdownPublicRingSendMessage['values'] = [ring for ring in keyRingSampledDataPublicRing if
                                                   ring.find("DSA") == -1]
    if dropdownPrivateRingExport is not None:
        dropdownPrivateRingExport['values'] = [ring for ring in keyRingSampledDataPrivateRing]
    if dropdownPublicRingExport is not None:
        dropdownPublicRingExport['values'] = [ring for ring in keyRingSampledDataPublicRing]

    if guiTabs is not None:
        fillTabRingShowcase(guiTabs[2])


def init():
    Interface.loadConfig()
    global guiTabs
    guiTabs = createTabs(notebook)


# Init tabs
def createTabs(curr_notebook):
    # Create tabs
    tb_key_pair_insert_delete = ttk.Frame(curr_notebook)
    tb_import_export_pem_keys = ttk.Frame(curr_notebook)
    tb_rings_showcase = ttk.Frame(curr_notebook)
    tb_send_mess = ttk.Frame(curr_notebook)
    tb_receive_mess = ttk.Frame(curr_notebook)

    # Add tabs to the notebook
    curr_notebook.add(tb_key_pair_insert_delete, text="key pair insertion / deletion")
    curr_notebook.add(tb_import_export_pem_keys, text="import / export of .pem keys")
    curr_notebook.add(tb_rings_showcase, text="public and private ring showcase")
    curr_notebook.add(tb_send_mess, text="send message")
    curr_notebook.add(tb_receive_mess, text="receive message")

    refreshRings()

    return [tb_key_pair_insert_delete, tb_import_export_pem_keys, tb_rings_showcase, tb_send_mess, tb_receive_mess]


# Tab 1
def fillTabKeyPairInsertion(curr_tab):
    def handle_button_click_insert():
        try:
            username: str = input_field_username.get()
            email: str = input_field_email.get()
            password: str = Interface.generateHash(input_field_password.get())
            if len(username) * len(email) * len(password) == 0 or email.index("@") == -1:
                raise Exception("Invalid input data")
            selected_algo = asymmetricAlgorithms.index(dropdown.get())
            if Interface.insertKeyIntoRings(username, email, password, selected_algo):
                textToLabel = "Successful insertion of key"
            else:
                textToLabel = "Unsuccessful insertion of key"
            refreshRings()
            result_label.configure(text=textToLabel)
        except Exception as e:
            print(e)
            result_label.configure(text="Failed insertion of key, error: " + str(e))

    def handle_button_click_delete():
        try:
            sampledDataForDel: str = dropdownDel.get()
            password: str = Interface.generateHash(input_field_password2.get())
            if Interface.deleteKeyFromRings(int(sampledDataForDel.split(", ")[-1]), password):
                textToLabel = "Successful deletion of key"
            else:
                textToLabel = "Unsuccessful deletion of key"
            refreshRings()
            result_label2.configure(text=textToLabel)
        except Exception as e:
            print(e)
            result_label2.configure(text="Failed deletion of key, error: " + str(e))

    def handle_button_save_config():
        try:
            Interface.storeConfig()
            result_label3.configure(text="Configuration saved")
        except Exception as e:
            print(e)

    ttk.Label(curr_tab, text="Insertion:").pack(padx=40, pady=5, anchor=tk.NW)

    ttk.Label(curr_tab, text="username").pack(padx=40, pady=5, anchor=tk.NW)
    input_field_username = ttk.Entry(curr_tab, width=50)
    input_field_username.pack(padx=40, pady=5, anchor=tk.NW)

    ttk.Label(curr_tab, text="email").pack(padx=40, pady=5, anchor=tk.NW)
    input_field_email = ttk.Entry(curr_tab, width=50)
    input_field_email.pack(padx=40, pady=5, anchor=tk.NW)

    ttk.Label(curr_tab, text="password").pack(padx=40, pady=5, anchor=tk.NW)
    input_field_password = ttk.Entry(curr_tab, show="*", width=30)
    input_field_password.pack(padx=40, pady=5, anchor=tk.NW)

    ttk.Label(curr_tab, text="Select an algorithm:").pack(padx=40, pady=5, anchor=tk.NW)
    dropdown = ttk.Combobox(curr_tab, values=asymmetricAlgorithms, state="readonly", width=15)
    dropdown.pack(padx=40, pady=5, anchor=tk.NW)

    button = ttk.Button(curr_tab, text="Insert", command=handle_button_click_insert)
    button.pack(padx=40, pady=5, anchor=tk.NW)

    result_label = ttk.Label(curr_tab, text="")
    result_label.pack(padx=40, pady=0, anchor=tk.NW)

    # ----------------------------------------------------------------------------------

    ttk.Separator(curr_tab, orient='horizontal').pack(padx=40, pady=5, anchor=tk.NW, fill='x')

    ttk.Label(curr_tab, text="Deletion:").pack(padx=40, pady=5, anchor=tk.NW)

    ttk.Label(curr_tab, text="Select ring pair to delete:").pack(padx=40, pady=5, anchor=tk.NW)

    global dropdownDel
    dropdownDel = ttk.Combobox(curr_tab, values=keyRingSampledData, state="readonly", width=80)
    dropdownDel.pack(padx=40, pady=5, anchor=tk.NW)

    ttk.Label(curr_tab, text="confirm password for ring").pack(padx=40, pady=5, anchor=tk.NW)
    input_field_password2 = ttk.Entry(curr_tab, show="*", width=30)
    input_field_password2.pack(padx=40, pady=5, anchor=tk.NW)

    button = ttk.Button(curr_tab, text="Delete", command=handle_button_click_delete)
    button.pack(padx=40, pady=5, anchor=tk.NW)

    result_label2 = ttk.Label(curr_tab, text="")
    result_label2.pack(padx=40, pady=0, anchor=tk.NW)

    # ----------------------------------------------------------------------------------

    ttk.Separator(curr_tab, orient='horizontal').pack(padx=40, pady=5, anchor=tk.NW, fill='x')

    button = ttk.Button(curr_tab, text="Save config", command=handle_button_save_config)
    button.pack(padx=40, pady=5, anchor=tk.NW)

    result_label3 = ttk.Label(curr_tab, text="")
    result_label3.pack(padx=40, pady=0, anchor=tk.NW)


# Tab 2
def fillTabImportExportPemKeys(curr_tab):
    # global keyRingSampledDataPrivateRing, keyRingSampledDataPublicRing
    global dropdownPrivateRingExport, dropdownPublicRingExport

    # -----------------------------PRIVATE KEY EXPORT--------------------------------

    def select_file_path_private():
        global pathForFileExport_private_export

        file_path = filedialog.asksaveasfilename(defaultextension=".pem")
        if file_path:
            file_label_private_export.config(text="Save private key as file: " + file_path)
            pathForFileExport_private_export = file_path
        else:
            file_label_private_export.config(text="Save private key as file: No file selected")
            pathForFileExport_private_export = ""

    def handle_button_click_private_export():
        try:
            Interface.exportKeyPairFromRings(
                int(dropdownPrivateRingExport.get().split(", ")[-1]),
                pathForFileExport_private_export
            )
            result_label_private_export.configure(
                text="Successfully exported private key, saved in file: " + pathForFileExport_private_export)
        except Exception as e:
            result_label_private_export.configure(text="Failed, error " + str(e))

    label = ttk.Label(curr_tab, text="Export private key:")
    label.pack(padx=40, pady=5, anchor=tk.NW)

    dropdownPrivateRingExport = ttk.Combobox(curr_tab, values=[], state="readonly", width=80)
    dropdownPrivateRingExport.pack(padx=40, pady=5, anchor=tk.NW)

    select_button = tk.Button(curr_tab, text="Select new file as export", command=select_file_path_private)
    select_button.pack(padx=20, pady=10)

    # Create a label to display the selected folder path
    file_label_private_export = tk.Label(curr_tab, text="Save as file: No file selected")
    file_label_private_export.pack(padx=20, pady=5)

    button2 = ttk.Button(curr_tab, text="Export to file", command=handle_button_click_private_export)
    button2.pack(padx=20, pady=5)

    result_label_private_export = ttk.Label(curr_tab, text="")
    result_label_private_export.pack(padx=20, pady=0)

    # -----------------------------PUBLIC KEY EXPORT--------------------------------
    def select_file_path_public():
        global pathForFileExport_public_export

        file_path = filedialog.asksaveasfilename(defaultextension=".pem")
        if file_path:
            file_label_public_export.config(text="Save public key as file: " + file_path)
            pathForFileExport_public_export = file_path
        else:
            file_label_public_export.config(text="Save public key as file: No file selected")
            pathForFileExport_public_export = ""

    def handle_button_click_public_export():
        try:
            Interface.exportPublicKey(
                int(dropdownPublicRingExport.get().split(", ")[-1]),
                pathForFileExport_public_export
            )
            result_label_public_export.configure(
                text="Successfully exported public key, saved in file: " + pathForFileExport_public_export)
        except Exception as e:
            result_label_public_export.configure(text="Failed, error " + str(e))

    label = ttk.Label(curr_tab, text="Export public key:")
    label.pack(padx=40, pady=0, anchor=tk.NW)

    dropdownPublicRingExport = ttk.Combobox(curr_tab, values=[], state="readonly", width=80)
    dropdownPublicRingExport.pack(padx=40, pady=5, anchor=tk.NW)

    select_button = tk.Button(curr_tab, text="Select new file as export", command=select_file_path_public)
    select_button.pack(padx=20, pady=10)

    # Create a label to display the selected folder path
    file_label_public_export = tk.Label(curr_tab, text="Save as file: No file selected")
    file_label_public_export.pack(padx=20, pady=5)

    button2 = ttk.Button(curr_tab, text="Export to file", command=handle_button_click_public_export)
    button2.pack(padx=20, pady=5)

    result_label_public_export = ttk.Label(curr_tab, text="")
    result_label_public_export.pack(padx=20, pady=0)

    # -----------------------------PRIVATE KEY IMPORT--------------------------------
    def select_file_path_src_private():
        global pathForFileImport_private_import
        file_path = filedialog.askopenfilename()
        if file_path:
            file_src_label_private.config(text="Source file path: " + file_path)
            pathForFileImport_private_import = file_path
        else:
            file_src_label_private.config(text="Source file path: No file selected")
            pathForFileImport_private_import = ""

    def handle_button_click_private_import():
        try:
            status = Interface.importKeyPairFromRings(
                pathForFileImport_private_import,
            )
            if status == "Success" or status is True:
                result_label_private_import.configure(
                    text="Successfully imported private key from file: " + pathForFileImport_private_import)
            else:
                result_label_private_import.configure(
                    text="Failed, error: " + str(status))
            refreshRings()
        except Exception as e:
            print(e)
            result_label_private_import.configure(text="Failed, error: " + str(e))

    ttk.Separator(curr_tab, orient='horizontal').pack(padx=40, pady=5, anchor=tk.NW, fill='x')

    label = ttk.Label(curr_tab, text="Import private key:")
    label.pack(padx=40, pady=0, anchor=tk.NW)

    select_button = tk.Button(curr_tab, text="Select file as source", command=select_file_path_src_private)
    select_button.pack(padx=20, pady=10)

    file_src_label_private = tk.Label(curr_tab, text="Source file path: No file selected")
    file_src_label_private.pack(padx=20, pady=5)

    button2 = ttk.Button(curr_tab, text="Import private key", command=handle_button_click_private_import)
    button2.pack(padx=20, pady=5)

    result_label_private_import = ttk.Label(curr_tab, text="")
    result_label_private_import.pack(padx=20, pady=0)

    # -----------------------------PUBLIC KEY IMPORT--------------------------------
    def select_file_path_src_public():
        global pathForFileImport_public_import
        file_path = filedialog.askopenfilename()
        if file_path:
            file_src_label_public.config(text="Source file path: " + file_path)
            pathForFileImport_public_import = file_path
        else:
            file_src_label_public.config(text="Source file path: No file selected")
            pathForFileImport_public_import = ""

    def handle_button_click_public_import():
        try:
            status = Interface.importPublicKey(
                pathForFileImport_public_import,
            )
            if status == "Success" or status is True:
                result_label_public_import.configure(
                    text="Successfully imported public key from file: " + pathForFileImport_public_import)
                refreshRings()
            else:
                result_label_public_import.configure(
                    text="Failed, error: " + str(status))
        except Exception as e:
            print(e)
            result_label_public_import.configure(text="Failed, error: " + str(e))

    label = ttk.Label(curr_tab, text="Import public key:")
    label.pack(padx=40, pady=0, anchor=tk.NW)

    select_button = tk.Button(curr_tab, text="Select file as source", command=select_file_path_src_public)
    select_button.pack(padx=20, pady=10)

    file_src_label_public = tk.Label(curr_tab, text="Source file path: No file selected")
    file_src_label_public.pack(padx=20, pady=5)

    button2 = ttk.Button(curr_tab, text="Import public key", command=handle_button_click_public_import)
    button2.pack(padx=20, pady=5)

    result_label_public_import = ttk.Label(curr_tab, text="")
    result_label_public_import.pack(padx=20, pady=5)


# Tab 3
def fillTabRingShowcase(curr_tab):
    global privateRing, publicRing, frameGridPrivate, frameGridPublic, labelsInTab3

    if frameGridPrivate is not None:
        frameGridPrivate.destroy()
    if frameGridPublic is not None:
        frameGridPublic.destroy()
    if labelsInTab3 is not None:
        for lab in labelsInTab3:
            lab.destroy()
    labelsInTab3 = []

    label = ttk.Label(curr_tab, text="Private ring:")
    label.pack(padx=40, pady=5, anchor=tk.NW)
    labelsInTab3.append(label)

    frameGridPrivate = tk.Frame(
        master=curr_tab,
        relief=tk.RAISED,
        borderwidth=2
    )

    frameGridPrivate.grid_columnconfigure("all", weight=1, uniform="fred")
    tk.Label(frameGridPrivate, text="Ord num\t", anchor="w").grid(row=0, column=0, sticky="ew")
    tk.Label(frameGridPrivate, text="Username\t", anchor="w").grid(row=0, column=1, sticky="ew")
    tk.Label(frameGridPrivate, text="Email\t\t", anchor="w").grid(row=0, column=2, sticky="ew")
    tk.Label(frameGridPrivate, text="Asymmetric algorithm\t", anchor="w").grid(row=0, column=3, sticky="ew")
    tk.Label(frameGridPrivate, text="Public key id\t\t", anchor="w").grid(row=0, column=4, sticky="ew")
    tk.Label(frameGridPrivate, text="Date created\t\t", anchor="w").grid(row=0, column=5, sticky="ew")
    tk.Label(frameGridPrivate, text="Sampled ring data", anchor="w").grid(row=0, column=6, sticky="ew")

    row = 1
    for ring in privateRing:
        tk.Label(frameGridPrivate, text=row, anchor="w").grid(row=row, column=0, sticky="ew")
        tk.Label(frameGridPrivate, text=ring.username, anchor="w").grid(row=row, column=1, sticky="ew")
        tk.Label(frameGridPrivate, text=ring.mail, anchor="w").grid(row=row, column=2, sticky="ew")
        tk.Label(frameGridPrivate, text=asymmetricAlgorithms[ring.idAsymAlgo], anchor="w").grid(row=row, column=3,
                                                                                                sticky="ew")
        tk.Label(frameGridPrivate, text=ring.idPublicKey, anchor="w").grid(row=row, column=4, sticky="ew")
        tk.Label(frameGridPrivate, text=str(ring.timestamp), anchor="w").grid(row=row, column=5, sticky="ew")
        tk.Button(frameGridPrivate, text="Show", command=lambda
            data=Interface.formSampleStringFromRings(ring, asymmetricAlgorithms): messagebox.showinfo(
            "Sample ring data", "Sampled data:\n\n" + data)).grid(row=row, column=6, sticky="ew")
        row += 1

    frameGridPrivate.pack(side="top", fill="both", expand=False, padx=10, pady=10)

    label = ttk.Label(curr_tab, text="")
    label.pack(padx=40, pady=5, anchor=tk.NW)
    labelsInTab3.append(label)

    label = ttk.Label(curr_tab, text="Public ring:")
    label.pack(padx=40, pady=5, anchor=tk.NW)
    labelsInTab3.append(label)

    frameGridPublic = tk.Frame(
        master=curr_tab,
        relief=tk.RAISED,
        borderwidth=2
    )

    frameGridPublic.grid_columnconfigure("all", weight=1, uniform="fred")
    tk.Label(frameGridPublic, text="Ord num\t", anchor="w").grid(row=0, column=0, sticky="ew")
    tk.Label(frameGridPublic, text="Username\t", anchor="w").grid(row=0, column=1, sticky="ew")
    tk.Label(frameGridPublic, text="Email\t\t", anchor="w").grid(row=0, column=2, sticky="ew")
    tk.Label(frameGridPublic, text="Asymmetric algorithm\t", anchor="w").grid(row=0, column=3, sticky="ew")
    tk.Label(frameGridPublic, text="Public key id\t\t", anchor="w").grid(row=0, column=4, sticky="ew")
    tk.Label(frameGridPublic, text="Date created\t\t", anchor="w").grid(row=0, column=5, sticky="ew")
    tk.Label(frameGridPublic, text="Sampled ring data", anchor="w").grid(row=0, column=6, sticky="ew")

    row = 1
    for ring in publicRing:
        tk.Label(frameGridPublic, text=row, anchor="w").grid(row=row, column=0, sticky="ew")
        tk.Label(frameGridPublic, text=ring.username, anchor="w").grid(row=row, column=1, sticky="ew")
        tk.Label(frameGridPublic, text=ring.mail, anchor="w").grid(row=row, column=2, sticky="ew")
        tk.Label(frameGridPublic, text=asymmetricAlgorithms[ring.idAsymAlgo], anchor="w").grid(row=row, column=3,
                                                                                               sticky="ew")
        tk.Label(frameGridPublic, text=ring.idPublicKey, anchor="w").grid(row=row, column=4, sticky="ew")
        tk.Label(frameGridPublic, text=str(ring.timestamp), anchor="w").grid(row=row, column=5, sticky="ew")
        tk.Button(frameGridPublic, text="Show", command=lambda
            data=Interface.formSampleStringFromRings(ring, asymmetricAlgorithms): messagebox.showinfo(
            "Sample ring data", "Sampled data:\n\n" + data)).grid(row=row, column=6, sticky="ew")

        row += 1

    frameGridPublic.pack(side="top", fill="both", expand=False, padx=10, pady=10)


# Tab 4
def fillTabSendMessage(curr_tab):
    def select_file_path():
        global pathForFileExport
        file_path = filedialog.asksaveasfilename(defaultextension=".pem")
        if file_path:
            file_label.config(text="Save as file: " + file_path)
            pathForFileExport = file_path
        else:
            file_label.config(text="Save as file: No file selected")
            pathForFileExport = ""

    def handle_button_click():
        try:
            needsSign = checkbox_var1.get() == 1
            needsEncr = checkbox_var2.get() == 1
            if needsSign:
                selectedPrivateRingPublicKeyId: int = int(dropdownPrivateRingSendMessage.get().split(", ")[-1])
            else:
                selectedPrivateRingPublicKeyId: int = 0
            if needsEncr:
                selectedPublicRingPublicKeyId: int = int(dropdownPublicRingSendMessage.get().split(", ")[-1])
                selectedSymmAlgoId: int = symmetricAlgorithms.index(dropdown.get())
            else:
                selectedPublicRingPublicKeyId: int = 0
                selectedSymmAlgoId: int = 0
            res = Interface.sendMessage(
                text_area.get("1.0", "end"),
                selectedPrivateRingPublicKeyId,
                selectedPublicRingPublicKeyId,
                needsSign,
                needsEncr,
                Interface.generateHash(input_field_password.get()),
                selectedSymmAlgoId,
                pathForFileExport
            )
            if res is not None:
                result_label.configure(text="Successfully sent message, saved in file: " + pathForFileExport)
            else:
                result_label.configure(text="Failed, invalid password ")
        except Exception as e:
            result_label.configure(text="Failed, error " + str(e))

    ttk.Label(curr_tab, text="Message:").pack(padx=40, pady=5, anchor=tk.NW)

    text_area = tk.Text(curr_tab, height=10, width=80)
    text_area.pack(padx=40, pady=10, anchor=tk.NW)

    ttk.Separator(curr_tab, orient='horizontal').pack(padx=40, pady=5, anchor=tk.NW, fill='x')

    global dropdownPrivateRingSendMessage, dropdownPublicRingSendMessage

    checkbox_var1 = tk.IntVar()
    tk.Checkbutton(curr_tab, text="Signature", variable=checkbox_var1).pack(padx=40, pady=5, anchor=tk.NW)

    ttk.Label(curr_tab, text="Select private ring:").pack(padx=40, pady=5, anchor=tk.NW)
    dropdownPrivateRingSendMessage = ttk.Combobox(curr_tab, values=[], state="readonly", width=80)
    dropdownPrivateRingSendMessage.pack(padx=40, pady=5, anchor=tk.NW)

    ttk.Label(curr_tab, text="Password for selected private ring (if selected option Signature):").pack(padx=40, pady=5,
                                                                                                        anchor=tk.NW)
    input_field_password = ttk.Entry(curr_tab, show="*", width=30)
    input_field_password.pack(padx=40, pady=5, anchor=tk.NW)

    ttk.Separator(curr_tab, orient='horizontal').pack(padx=40, pady=5, anchor=tk.NW, fill='x')

    checkbox_var2 = tk.IntVar()
    tk.Checkbutton(curr_tab, text="Encrypt", variable=checkbox_var2).pack(padx=40, pady=5, anchor=tk.NW)

    ttk.Label(curr_tab, text="Select public ring:").pack(padx=40, pady=5, anchor=tk.NW)
    dropdownPublicRingSendMessage = ttk.Combobox(curr_tab, values=[], state="readonly", width=80)
    dropdownPublicRingSendMessage.pack(padx=40, pady=5, anchor=tk.NW)

    ttk.Label(curr_tab, text="Select an algorithm:").pack(padx=40, pady=5, anchor=tk.NW)
    dropdown = ttk.Combobox(curr_tab, values=symmetricAlgorithms, state="readonly", width=15)
    dropdown.pack(padx=40, pady=5, anchor=tk.NW)

    ttk.Separator(curr_tab, orient='horizontal').pack(padx=40, pady=5, anchor=tk.NW, fill='x')

    # Create a button to trigger folder selection
    select_button = tk.Button(curr_tab, text="Select new file as export", command=select_file_path)
    select_button.pack(padx=20, pady=10)

    button2 = ttk.Button(curr_tab, text="Export to file", command=handle_button_click)
    button2.pack(padx=20, pady=10)

    # Create a label to display the selected folder path
    file_label = tk.Label(curr_tab, text="Save as file: No file selected")
    file_label.pack(padx=20, pady=5)

    result_label = ttk.Label(curr_tab, text="")
    result_label.pack(padx=20, pady=10)


# Tab 5
def fillTabReceiveMessage(curr_tab):
    def select_file_path_src():
        global pathForFileImport
        file_path = filedialog.askopenfilename()
        if file_path:
            file_src_label.config(text="Source file path: " + file_path)
            pathForFileImport = file_path
        else:
            file_src_label.config(text="Source file path: No file selected")
            pathForFileImport = ""

    def select_file_path_dest():
        global pathForFileExport
        file_path = filedialog.asksaveasfilename(defaultextension=".txt")
        if file_path:
            file_dest_label.config(text="Destination file path: " + file_path)
            pathForFileExport = file_path
        else:
            file_dest_label.config(text="Destination file path: No file selected")
            pathForFileExport = ""

    def handle_button_click():
        try:
            status = Interface.receiveMessageFromFile(
                pathForFileImport,
                Interface.generateHash(input_field_password.get()),
                pathForFileExport
            )
            if status is None:  # status == "Success" or status is True:
                result_label.configure(text="Successfully received message, saved in file: " + pathForFileExport)
            elif isinstance(status, str) and status.find("Succ") != -1:
                result_label.configure(
                    text="Successfully received message, saved in file: " + pathForFileExport + "\n" + status)
            else:
                result_label.configure(text=status)
        except Exception as e:
            print(e)
            result_label.configure(text="Failed, error: " + str(e))

    ttk.Label(curr_tab, text="Password for selected private ring (if selected option Encryption):").pack(padx=40,
                                                                                                         pady=5,
                                                                                                         anchor=tk.NW)
    input_field_password = ttk.Entry(curr_tab, show="*", width=30)
    input_field_password.pack(padx=40, pady=5, anchor=tk.NW)

    ttk.Separator(curr_tab, orient='horizontal').pack(padx=40, pady=5, anchor=tk.NW, fill='x')

    # Create a button to trigger folder selection
    select_button = tk.Button(curr_tab, text="Select file as source", command=select_file_path_src)
    select_button.pack(padx=20, pady=10)

    file_src_label = tk.Label(curr_tab, text="Source file path: No file selected")
    file_src_label.pack(padx=20, pady=5)

    ttk.Separator(curr_tab, orient='horizontal').pack(padx=40, pady=5, anchor=tk.NW, fill='x')

    select_button = tk.Button(curr_tab, text="Select new file as destination", command=select_file_path_dest)
    select_button.pack(padx=20, pady=10)

    file_dest_label = tk.Label(curr_tab, text="Destination file path: No file selected")
    file_dest_label.pack(padx=20, pady=5)

    ttk.Separator(curr_tab, orient='horizontal').pack(padx=40, pady=5, anchor=tk.NW, fill='x')

    button2 = ttk.Button(curr_tab, text="Export to file", command=handle_button_click)
    button2.pack(padx=20, pady=10)

    result_label = ttk.Label(curr_tab, text="")
    result_label.pack(padx=20, pady=10)


if __name__ == "__main__":
    guiTabs: List[tk.Frame] = None
    asymmetricAlgorithms: List[str] = ["RSA", "DSA", "ElGamal 1024", "ElGamal 2048"]
    symmetricAlgorithms: List[str] = ["AES 128", "CAST 5"]
    publicRing: List[PublicRingToken]
    privateRing: List[PrivateRingToken]
    keyRingSampledData: List[str] = []
    dropdownDel: ttk.Combobox = None

    pathForFileExport_private_export: str = "-/-"
    pathForFileExport_public_export: str = "-/-"
    pathForFileImport_private_import: str = "-/-"
    pathForFileImport_public_import: str = "-/-"

    dropdownPrivateRingSendMessage: ttk.Combobox = None
    dropdownPublicRingSendMessage: ttk.Combobox = None
    keyRingSampledDataPrivateRing: List[str] = []
    keyRingSampledDataPublicRing: List[str] = []

    dropdownPrivateRingExport: ttk.Combobox = None
    dropdownPublicRingExport: ttk.Combobox = None

    frameGridPrivate: tk.Frame = None
    frameGridPublic: tk.Frame = None
    labelsInTab3: List[ttk.Label] = None

    pathForFileExport: str = ""
    pathForFileImport: str = ""

    window = tk.Tk()
    window.geometry("900x760")
    window.title("ZP Projekat")

    notebook = ttk.Notebook(window)

    init()

    # --------------------------------------------------------

    fillTabKeyPairInsertion(guiTabs[0])
    fillTabImportExportPemKeys(guiTabs[1])
    fillTabRingShowcase(guiTabs[2])
    fillTabSendMessage(guiTabs[3])
    fillTabReceiveMessage(guiTabs[4])
    refreshRings()

    # --------------------------------------------------------

    notebook.pack(expand=True, fill="both")
    window.mainloop()
